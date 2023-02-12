package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FuelAction {
  OUT_OF_FUEL("Out of fuel");

  private final String value;
}
