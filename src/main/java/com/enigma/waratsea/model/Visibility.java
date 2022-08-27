package com.enigma.waratsea.model;

import lombok.RequiredArgsConstructor;

/**
 * Represents the current visibility.
 */
@RequiredArgsConstructor
public enum Visibility {
  GOOD("Good"),
  BAD("Bad");

  private final String value;

  @Override
  public String toString() {
    return value;
  }

}
