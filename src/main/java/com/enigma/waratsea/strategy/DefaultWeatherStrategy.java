package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.weather.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.dto.WeatherDto;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
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

  @Override
  public WeatherType determine(final WeatherDto input) {
    var currentWeather = input.getWeather();
    var die = diceService.get().roll();

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
