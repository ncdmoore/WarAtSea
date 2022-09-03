package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Weather;

public interface WeatherService {
   Weather determine(WeatherInput input);
}
