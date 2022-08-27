package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.WeatherService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.function.Function;

@Singleton
public class DefaultWeatherStrategy implements WeatherStrategy {
  private final Map<Integer, Function<WeatherType, WeatherType>> weatherFunctions = Map.of(
      1, this::improve,
      2, this::improve,
      3, this::noChange,
      4, this::noChange,
      5, this::worsen,
      6, this::worsen
  );

  private final DiceService diceService;

  @Inject
  public DefaultWeatherStrategy(final DiceService diceService) {
    this.diceService = diceService;
  }

  @Override
  public WeatherType determine(WeatherService.WeatherInput input) {
    var currentWeather = input.getWeather();
    var die = diceService.roll();

    return weatherFunctions
        .get(die)
        .apply(currentWeather.getWeatherType());
  }

  private WeatherType improve(final WeatherType currentWeather) {
    return currentWeather.improve();
  }

  private WeatherType noChange(final WeatherType currentWeather) {
    return currentWeather;
  }

  private WeatherType worsen(final WeatherType currentWeather) {
    return currentWeather.worsen();
  }
}
