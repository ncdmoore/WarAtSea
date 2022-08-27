package com.enigma.watatsea.service;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.WeatherService;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.enigma.waratsea.strategy.WeatherStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.TimeRange.DAY_1;
import static com.enigma.waratsea.model.Visibility.GOOD;
import static com.enigma.waratsea.model.WeatherType.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceTest {
  private WeatherService weatherService;

  private WeatherService.WeatherInput input;

  @BeforeEach
  void setUp() {
    var currentWeather = Weather.builder().weatherType(CLEAR).build();
    var turn = Turn.builder().timeRange(DAY_1).build();
    input = WeatherService.WeatherInput.builder().weather(currentWeather).turn(turn).build();

    var events = new Events();
    WeatherStrategy weatherStrategy = game -> CLEAR;
    VisibilityStrategy visibilityStrategy = game -> GOOD;
    Map<GameName, WeatherStrategy> weatherStrategyMap = Map.of(BOMB_ALLEY, weatherStrategy);
    Map<GameName, VisibilityStrategy> visibilityStrategyMap = Map.of(BOMB_ALLEY, visibilityStrategy);

    weatherService = new WeatherService(
        events,
        weatherStrategy,
        visibilityStrategy,
        weatherStrategyMap,
        visibilityStrategyMap);

    var gameNameEvent = new GameNameEvent(BOMB_ALLEY);
    events.getGameNameEvents().fire(gameNameEvent);
  }

  @Test
  void testDetermine() {
    var newWeather = weatherService.determine(input);

    assertEquals(CLEAR, newWeather.getWeatherType());
    assertEquals(GOOD, newWeather.getVisibility());
  }
}
