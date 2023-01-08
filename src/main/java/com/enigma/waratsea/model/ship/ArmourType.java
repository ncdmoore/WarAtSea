package com.enigma.waratsea.model.ship;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArmourType {
  HEAVY("Heavy"),
  LIGHT("Light"),
  NONE("None");

  private final String value;
}
