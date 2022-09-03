package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.WeatherType;
import com.enigma.waratsea.service.WeatherInput;

public interface WeatherStrategy {
  WeatherType determine(WeatherInput input);
}
