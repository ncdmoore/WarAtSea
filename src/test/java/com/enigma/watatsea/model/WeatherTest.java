package com.enigma.watatsea.model;

import com.enigma.waratsea.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.WeatherType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherTest {
  private Weather weather;

  @BeforeEach
  void setUp() {
    weather = new Weather();
  }

  @Test
  void worsenTest() {
    assertEquals(CLOUDY, weather.worsen(CLEAR));
    assertEquals(RAIN, weather.worsen(CLOUDY));
    assertEquals(SQUALL, weather.worsen(RAIN));
    assertEquals(STORM, weather.worsen(SQUALL));
    assertEquals(GALE, weather.worsen(STORM));
    assertEquals(GALE, weather.worsen(GALE));
  }

  @Test
  void improveTest() {
    assertEquals(CLEAR, weather.improve(CLEAR));
    assertEquals(CLEAR, weather.improve(CLOUDY));
    assertEquals(CLOUDY, weather.improve(RAIN));
    assertEquals(RAIN, weather.improve(SQUALL));
    assertEquals(SQUALL, weather.improve(STORM));
    assertEquals(STORM, weather.improve(GALE));
  }

  @Test
  void noChange() {
    assertEquals(CLEAR, weather.noChange(CLEAR));
    assertEquals(CLOUDY, weather.noChange(CLOUDY));
    assertEquals(RAIN, weather.noChange(RAIN));
    assertEquals(SQUALL, weather.noChange(SQUALL));
    assertEquals(STORM, weather.noChange(STORM));
    assertEquals(GALE, weather.noChange(GALE));
  }
}
