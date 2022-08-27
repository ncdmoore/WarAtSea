package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.WeatherType;
import com.enigma.waratsea.service.WeatherService;

public interface WeatherStrategy {
  WeatherType determine(WeatherService.WeatherInput input);
}
