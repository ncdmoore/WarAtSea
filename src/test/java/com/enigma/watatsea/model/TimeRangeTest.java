package com.enigma.watatsea.model;

import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.TimeRange.DAY_1;
import static com.enigma.waratsea.model.TimeRange.DAY_2;
import static com.enigma.waratsea.model.TimeRange.DAY_3;
import static com.enigma.waratsea.model.TimeRange.NIGHT_1;
import static com.enigma.waratsea.model.TimeRange.NIGHT_2;
import static com.enigma.waratsea.model.TimeRange.TWILIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeRangeTest {
  @Test
  void testNextTimeRange() {
    assertEquals(DAY_2, DAY_1.next());
    assertEquals(DAY_3, DAY_2.next());
    assertEquals(TWILIGHT, DAY_3.next());
    assertEquals(NIGHT_1, TWILIGHT.next());
    assertEquals(NIGHT_2, NIGHT_1.next());
    assertEquals(DAY_1, NIGHT_2.next());
  }
}
