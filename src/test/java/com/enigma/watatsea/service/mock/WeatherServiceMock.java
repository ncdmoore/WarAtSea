package com.enigma.watatsea.service.mock;

import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.service.WeatherService;

public class WeatherServiceMock implements WeatherService {
  @Override
  public Weather determine(WeatherInput input) {
    return null;
  }
}
