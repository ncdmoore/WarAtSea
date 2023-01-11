package com.enigma.waratsea.model.aircraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LandingType {
  CARRIER("Carrier"),
  FLOAT_PLANE("Float-plane"),
  LAND("Land"),
  SEAPLANE("Sea-plane");

  private final String value;
}
