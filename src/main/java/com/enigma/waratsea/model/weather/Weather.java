package com.enigma.waratsea.model.weather;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
  private WeatherType weatherType;
  private Visibility visibility;
}
