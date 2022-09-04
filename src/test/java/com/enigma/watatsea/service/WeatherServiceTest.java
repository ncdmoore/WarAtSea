package com.enigma.watatsea.service;

import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.service.impl.WeatherServiceImpl;
import com.enigma.waratsea.strategy.DefaultVisibilityStrategy;
import com.enigma.waratsea.strategy.DefaultWeatherStrategy;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.enigma.waratsea.strategy.WeatherStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.enigma.waratsea.model.TimeRange.DAY_1;
import static com.enigma.waratsea.model.Visibility.GOOD;
import static com.enigma.waratsea.model.WeatherType.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
  @InjectMocks
  private WeatherServiceImpl weatherService;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  @Mock
  private DefaultWeatherStrategy defaultWeatherStrategy;

  @Mock
  private DefaultVisibilityStrategy defaultVisibilityStrategy;

  @SuppressWarnings("unused")
  @Mock
  private Map<GameName, WeatherStrategy> weatherStrategyMap;

  @SuppressWarnings("unused")
  @Mock
  private Map<GameName, VisibilityStrategy> visibilityStrategyMap;

  @Test
  void testDetermineWeather() {
    var currentWeather = Weather.builder().weatherType(CLEAR).build();
    var turn = Turn.builder().timeRange(DAY_1).build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(defaultVisibilityStrategy.determine(input.getTurn())).willReturn(GOOD);
    given(defaultWeatherStrategy.determine(input)).willReturn(CLEAR);

    var newWeather = weatherService.determine(input);

    assertEquals(CLEAR, newWeather.getWeatherType());
    assertEquals(GOOD, newWeather.getVisibility());
  }
}
