package com.enigma.waratsea.strategy.arcticConvoy;

import com.enigma.waratsea.dto.WeatherDto;
import com.enigma.waratsea.model.weather.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static com.enigma.waratsea.model.weather.WeatherType.CLOUDY;
import static com.enigma.waratsea.model.weather.WeatherType.GALE;
import static com.enigma.waratsea.model.weather.WeatherType.RAIN;
import static com.enigma.waratsea.model.weather.WeatherType.SQUALL;
import static com.enigma.waratsea.model.weather.WeatherType.STORM;

@Singleton
public class ArcticConvoyWeatherStrategy implements WeatherStrategy {
  private final Set<Month> summerMonths = Set.of(
      Month.JUNE,
      Month.JULY,
      Month.AUGUST,
      Month.SEPTEMBER
  );

  private final Map<WeatherType, WeatherType> summerRollOne = Map.of(
      CLEAR, CLEAR,
      CLOUDY, CLEAR
  );

  private final Map<WeatherType, WeatherType> summerRollTwo = Map.of(
      CLEAR, CLEAR,
      CLOUDY, CLEAR,
      RAIN, CLOUDY
  );

  private final Map<WeatherType, WeatherType> summerRollThree = Map.of(
      CLEAR, CLOUDY,
      CLOUDY, CLOUDY,
      STORM, SQUALL
  );

  private final Map<WeatherType, WeatherType> summerRollFour = Map.of(
      CLEAR, CLOUDY,
      CLOUDY, CLOUDY,
      RAIN, RAIN
  );

  private final Map<WeatherType, WeatherType> summerRollFive = Map.of(
      CLEAR, CLOUDY,
      CLOUDY, RAIN,
      RAIN, SQUALL,
      SQUALL, SQUALL
  );

  private final Map<WeatherType, WeatherType> winterRollTwo = Map.of(
      CLEAR, CLEAR,
      CLOUDY, CLEAR
  );

  private final Map<WeatherType, WeatherType> winterRollThree = Map.of(
      CLEAR, CLEAR,
      CLOUDY, CLOUDY
  );

  private final Map<WeatherType, WeatherType> winterRollFour = Map.of(
      CLEAR, CLOUDY,
      CLOUDY, RAIN,
      RAIN, RAIN
  );

  private final Map<WeatherType, WeatherType> winterRollFive = Map.of(
      CLEAR, RAIN,
      CLOUDY, RAIN,
      RAIN, SQUALL,
      SQUALL, SQUALL
  );

  private final Map<WeatherType, WeatherType> winterRollSix = Map.of(
      CLEAR, SQUALL,
      CLOUDY, SQUALL,
      RAIN, SQUALL,
      SQUALL, STORM
  );

  private final Map<Integer, BiFunction<WeatherType, Month, WeatherType>> weatherMap = Map.of(
      1, this::rollOne,
      2, this::rollTwo,
      3, this::rollThree,
      4, this::rollFour,
      5, this::rollFive,
      6, this::rollSix
  );

  private final DiceService diceService;

  @Inject
  public ArcticConvoyWeatherStrategy(final DiceService diceService) {
    this.diceService = diceService;
  }

  @Override
  public WeatherType determine(final WeatherDto dto) {
    var currentWeather = dto.getWeather().getWeatherType();
    var currentMonth = dto.getTurn().getDate().getMonth();

    var die = diceService.get().roll();

    return weatherMap.get(die).apply(currentWeather, currentMonth);
  }

  private WeatherType rollOne(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? summerRollOne.getOrDefault(currentWeather, CLOUDY)
        : CLEAR;
  }

  private WeatherType rollTwo(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? summerRollTwo.getOrDefault(currentWeather, RAIN)
        : winterRollTwo.getOrDefault(currentWeather, CLOUDY);
  }

  private WeatherType rollThree(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? summerRollThree.getOrDefault(currentWeather, RAIN)
        : winterRollThree.getOrDefault(currentWeather, RAIN);
  }

  private WeatherType rollFour(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? summerRollFour.getOrDefault(currentWeather, SQUALL)
        : winterRollFour.getOrDefault(currentWeather, SQUALL);
  }

  private WeatherType rollFive(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? summerRollFive.getOrDefault(currentWeather, STORM)
        : winterRollFive.getOrDefault(currentWeather, STORM);
  }
  private WeatherType rollSix(final WeatherType currentWeather, final Month currentMonth) {
    return isSummer(currentMonth)
        ? currentWeather.worsen()
        : getWinterRollSix(currentWeather);
  }

  private WeatherType getWinterRollSix(final WeatherType currentWeather) {
    final var midValue = 3;

    if (currentWeather == STORM) {
      return diceService.get().roll() > midValue ? GALE : STORM;
    }

    return winterRollSix.getOrDefault(currentWeather, STORM);
  }

  private boolean isSummer(final Month month) {
    return summerMonths.contains(month);
  }
}
