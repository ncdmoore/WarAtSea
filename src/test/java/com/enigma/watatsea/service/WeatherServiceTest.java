package com.enigma.watatsea.service;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import com.enigma.waratsea.service.GameService;
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

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;
import static com.enigma.waratsea.model.weather.Visibility.GOOD;
import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static com.enigma.waratsea.model.weather.WeatherType.CLOUDY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
  @InjectMocks
  private WeatherServiceImpl weatherService;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  @Mock
  private GameService gameService;

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

    var game = new Game(BOMB_ALLEY);
    game.setWeather(currentWeather);
    game.setTurn(turn);

    given(gameService.getGame()).willReturn(game);
    given(defaultVisibilityStrategy.determine(any())).willReturn(GOOD);
    given(defaultWeatherStrategy.determine(any())).willReturn(CLOUDY);

    weatherService.determine();

    var newWeather = game.getWeather();

    assertEquals(CLOUDY, newWeather.getWeatherType());
    assertEquals(GOOD, newWeather.getVisibility());
  }
}
