package com.enigma.waratsea.model;

import com.google.inject.Singleton;

import java.util.Map;

import static com.enigma.waratsea.model.WeatherType.*;


@Singleton
public class Weather {
  private final Map<WeatherType, WeatherType> worsenMap = Map.of(
      CLEAR, CLOUDY,    // CLEAR worsens to CLOUDY, etc.
      CLOUDY, RAIN,
      RAIN, SQUALL,
      SQUALL, STORM,
      STORM, GALE,
      GALE, GALE
  );

  private final Map<WeatherType, WeatherType> improveMap = Map.of(
      CLEAR, CLEAR,
      CLOUDY, CLEAR,
      RAIN, CLOUDY,
      SQUALL, RAIN,
      STORM, SQUALL,
      GALE, STORM      // GALE improves to STORM, etc.
  );

  public WeatherType worsen(final WeatherType currentWeather) {
    return worsenMap.get(currentWeather);
  }

  public WeatherType improve(final WeatherType currentWeather) {
    return improveMap.get(currentWeather);
  }

  public WeatherType noChange(final WeatherType currentWeather) {
    return currentWeather;
  }
}
