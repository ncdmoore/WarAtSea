package com.enigma.waratsea.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
  private WeatherType weatherType;
  private Visibility visibility;
}
