package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TurnIndex {
  DAY_1(0, "06:00 - 10:00"),
  DAY_2(1, "10:00 - 14:00"),
  DAY_3(2, "14:00 - 18:00"),
  TWILIGHT(3, "18:00 - 22:00"),
  NIGHT_1(4, "22:00 - 02:00"),
  NIGHT_2(5, "02:00 - 06:00");

  private final int value;
  private final String timeRange;
}
