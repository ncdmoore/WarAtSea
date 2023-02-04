package com.enigma.waratsea.model.weather;

import lombok.RequiredArgsConstructor;

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
