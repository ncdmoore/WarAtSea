package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A game turn represents a 4-hour period of time.
 */
@Getter
@RequiredArgsConstructor
public enum TurnType {
  DAY_1("06:00 - 10:00"),
  DAY_2("10:00 - 14:00"),
  DAY_3("14:00 - 18:00"),
  TWILIGHT("18:00 - 22:00"),
  NIGHT_1("22:00 - 02:00"),
  NIGHT_2("02:00 - 06:00");

  private final String timeRange;
}
