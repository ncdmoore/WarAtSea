package com.enigma.waratsea.strategy.bombAlley;

import com.enigma.waratsea.model.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Singleton
public class BombAlleyWeatherStrategy implements WeatherStrategy {
  private final Set<Month> winterMonths = Set.of(
      Month.JANUARY,
      Month.FEBRUARY,
      Month.OCTOBER,
      Month.NOVEMBER,
      Month.DECEMBER
  );

  private final Map<Integer, BiFunction<WeatherType, Month, WeatherType>> weatherFunctions = Map.of(
      1, this::improve,
      2, this::improve,
      3, this::noChange,
      4, this::noChange,
      5, this::monthDependent,
      6, this::worsen
  );

  private final DiceService diceService;

  @Inject
  public BombAlleyWeatherStrategy(final DiceService diceService) {
    this.diceService = diceService;
  }

  @Override
  public WeatherType determine(final WeatherInput input) {
    var currentWeather = input.getWeather();
    var currentTurn = input.getTurn();
    var currentMonth = currentTurn.getDate().getMonth();
    var die = diceService.get().roll();

    return weatherFunctions
        .get(die)
        .apply(currentWeather.getWeatherType(), currentMonth);
  }

  private WeatherType improve(final WeatherType currentWeather, final Month month) {
    return currentWeather.improve();
  }

  private WeatherType noChange(final WeatherType currentWeather, final Month month) {
    return currentWeather;
  }

  private WeatherType monthDependent(final WeatherType currentWeather, final Month month) {
    return winterMonths.contains(month) ? currentWeather.worsen() : currentWeather;
  }

  private WeatherType worsen(final WeatherType currentWeather, final Month month) {
    return currentWeather.worsen();
  }
}
