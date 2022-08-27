package com.enigma.watatsea.model;

import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.WeatherType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherTypeTest {
  @Test
  void testWorsen() {
    assertEquals(CLOUDY, CLEAR.worsen());
    assertEquals(RAIN, CLOUDY.worsen());
    assertEquals(SQUALL, RAIN.worsen());
    assertEquals(STORM, SQUALL.worsen());
    assertEquals(GALE, STORM.worsen());
    assertEquals(GALE, GALE.worsen());
  }

  @Test
  void testImprove() {
    assertEquals(CLEAR, CLEAR.improve());
    assertEquals(CLEAR, CLOUDY.improve());
    assertEquals(CLOUDY, RAIN.improve());
    assertEquals(RAIN, SQUALL.improve());
    assertEquals(SQUALL, STORM.improve());
    assertEquals(STORM, GALE.improve());
  }
}
