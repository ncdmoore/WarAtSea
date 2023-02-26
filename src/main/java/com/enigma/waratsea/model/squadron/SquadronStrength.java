package com.enigma.waratsea.model.squadron;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SquadronStrength {
  FULL("Full"),
  HALF("Half"),
  SIXTH("Sixth");

  private final String value;
}
