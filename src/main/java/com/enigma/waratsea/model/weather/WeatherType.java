package com.enigma.waratsea.model.weather;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum WeatherType {
  CLEAR("Clear"),
  CLOUDY("Cloudy"),
  RAIN("Rain"),
  SQUALL("Squall"),
  STORM("Storm"),
  GALE("Gale");

  private static final Map<WeatherType, WeatherType> IMPROVE = Map.of(
    CLEAR, CLEAR,
    CLOUDY, CLEAR,
    RAIN, CLOUDY,
    SQUALL, RAIN,
    STORM, SQUALL,
    GALE, STORM
  );

  private static final Map<WeatherType, WeatherType> WORSEN = Map.of(
    CLEAR, CLOUDY,
    CLOUDY, RAIN,
    RAIN, SQUALL,
    SQUALL, STORM,
    STORM, GALE,
    GALE, GALE
  );

  private final String value;

  public WeatherType improve() {
    return IMPROVE.get(this);
  }

  public WeatherType worsen() {
    return WORSEN.get(this);
  }

  @Override
  public String toString() {
    return value;
  }
}
