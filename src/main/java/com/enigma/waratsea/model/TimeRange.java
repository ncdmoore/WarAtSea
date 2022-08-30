package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum TimeRange {
  DAY_1("06:00 - 10:00"),
  DAY_2("10:00 - 14:00"),
  DAY_3("14:00 - 18:00"),
  TWILIGHT("18:00 - 22:00"),
  NIGHT_1("22:00 - 02:00"),
  NIGHT_2("02:00 - 06:00");

  private static final Map<TimeRange, TimeRange> NEXT_TIME_RANGE = Map.of(
      DAY_1, DAY_2,        // DAY_1's next index is DAY_2, etc.
      DAY_2, DAY_3,
      DAY_3, TWILIGHT,
      TWILIGHT, NIGHT_1,
      NIGHT_1, NIGHT_2,
      NIGHT_2, DAY_1
  );

  private final String timeRange;

  public TimeRange next() {
    return NEXT_TIME_RANGE.get(this);
  }
}
