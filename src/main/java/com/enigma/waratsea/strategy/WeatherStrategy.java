package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.weather.WeatherType;
import com.enigma.waratsea.dto.WeatherDto;

public interface WeatherStrategy {
  WeatherType determine(WeatherDto input);
}
