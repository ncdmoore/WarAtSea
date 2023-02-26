package com.enigma.waratsea.model.aircraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AltitudeType {
  HIGH("High"),
  LOW("Low"),
  MEDIUM("Medium");

  private final String value;
}
