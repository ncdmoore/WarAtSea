package com.enigma.waratsea.strategy.arcticConvoy;

import com.enigma.waratsea.model.weather.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.Month;
import java.util.Map;
import java.util.Set;

import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static com.enigma.waratsea.model.weather.WeatherType.CLOUDY;
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

  private final DiceService diceService;

  @Inject
  ArcticConvoyWeatherStrategy(final DiceService diceService) {
    this.diceService = diceService;
  }

  @Override
  public WeatherType determine(final WeatherInput input) {
    var die = diceService.get().roll();
    return null;
  }

  private WeatherType getSummerRollOne(final WeatherType currentWeather) {
    return summerRollOne.getOrDefault(currentWeather, CLOUDY);
  }

  private WeatherType getSummerRollTwo(final WeatherType currentWeather) {
    return summerRollTwo.getOrDefault(currentWeather, RAIN);
  }

  private WeatherType getSummerRollThree(final WeatherType currentWeather) {
    return summerRollThree.getOrDefault(currentWeather, RAIN);
  }

  private WeatherType getSummerRollFour(final WeatherType currentWeather) {
    return summerRollFour.getOrDefault(currentWeather, SQUALL);
  }

  private WeatherType getSummerRollFive(final WeatherType currentWeather) {
    return summerRollFive.getOrDefault(currentWeather, STORM);
  }

  private WeatherType getWinterRollTwo(final WeatherType currentWeather) {
    return winterRollTwo.getOrDefault(currentWeather, CLOUDY);
  }

}
