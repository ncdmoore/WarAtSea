package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LandingType {
  CARRIER("Carrier"),
  FLOATPLANE("Float-plane"),
  LAND("Land"),
  SEAPLANE("Sea-plane");

  private final String value;
}
