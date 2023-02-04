package com.enigma.waratsea.model.map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GridType {
  SEA_DEEP("Deep sea"),
  SEA_SHALLOW("Shallow sea"),
  LAND("Land"),
  BOTH("Coast");

  private final String title;

  public String toString() {
    return title;
  }
}
