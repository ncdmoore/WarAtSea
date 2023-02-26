package com.enigma.waratsea.model.aircraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
  AIR_FORCE("Air Force"),
  NAVY("Navy");

  private final String value;
}
