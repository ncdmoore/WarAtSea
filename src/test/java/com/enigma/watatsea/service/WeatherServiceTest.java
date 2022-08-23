package com.enigma.watatsea.service;

import com.enigma.waratsea.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.WeatherType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {
  private WeatherService weatherService;

  @BeforeEach
  void setUp() {
    weatherService = new WeatherService();
  }

  @Test
  void worsenTest() {
    assertEquals(CLOUDY, weatherService.worsen(CLEAR));
    assertEquals(RAIN, weatherService.worsen(CLOUDY));
    assertEquals(SQUALL, weatherService.worsen(RAIN));
    assertEquals(STORM, weatherService.worsen(SQUALL));
    assertEquals(GALE, weatherService.worsen(STORM));
    assertEquals(GALE, weatherService.worsen(GALE));
  }

  @Test
  void improveTest() {
    assertEquals(CLEAR, weatherService.improve(CLEAR));
    assertEquals(CLEAR, weatherService.improve(CLOUDY));
    assertEquals(CLOUDY, weatherService.improve(RAIN));
    assertEquals(RAIN, weatherService.improve(SQUALL));
    assertEquals(SQUALL, weatherService.improve(STORM));
    assertEquals(STORM, weatherService.improve(GALE));
  }

  @Test
  void noChange() {
    assertEquals(CLEAR, weatherService.noChange(CLEAR));
    assertEquals(CLOUDY, weatherService.noChange(CLOUDY));
    assertEquals(RAIN, weatherService.noChange(RAIN));
    assertEquals(SQUALL, weatherService.noChange(SQUALL));
    assertEquals(STORM, weatherService.noChange(STORM));
    assertEquals(GALE, weatherService.noChange(GALE));
  }
}
