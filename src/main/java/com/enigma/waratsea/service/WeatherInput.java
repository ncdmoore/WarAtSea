package com.enigma.waratsea.service;

import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherInput {
  private Weather weather;
  private Turn turn;
}
