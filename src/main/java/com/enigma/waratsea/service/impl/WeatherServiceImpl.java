package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.phase.WeatherEvent;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.weather.Weather;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.dto.WeatherDto;
import com.enigma.waratsea.service.WeatherService;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Singleton
public class WeatherServiceImpl implements WeatherService {
  private final WeatherStrategy defaultWeatherStrategy;
  private final VisibilityStrategy defaultVisibilityStrategy;
  private final Map<GameName, WeatherStrategy> weatherStrategies;
  private final Map<GameName, VisibilityStrategy> visibilityStrategies;

  private WeatherStrategy weatherStrategy;
  private VisibilityStrategy visibilityStrategy;

  private final GameService gameService;

  @Inject
  public WeatherServiceImpl(final Events events,
                            final @Named("Default") WeatherStrategy defaultWeatherStrategy,
                            final @Named("Default") VisibilityStrategy defaultVisibilityStrategy,
                            final Map<GameName, WeatherStrategy> weatherStrategies,
                            final Map<GameName, VisibilityStrategy> visibilityStrategies,
                            final GameService gameService) {
    this.defaultWeatherStrategy = defaultWeatherStrategy;
    this.defaultVisibilityStrategy = defaultVisibilityStrategy;
    this.weatherStrategies = weatherStrategies;
    this.visibilityStrategies = visibilityStrategies;

    visibilityStrategy = defaultVisibilityStrategy;
    weatherStrategy = defaultWeatherStrategy;

    this.gameService = gameService;

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvent().register(this::setGameName);
    events.getWeatherEvent().register(this::handleWeatherEvent);
  }

  private void setGameName(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName();

    weatherStrategy = weatherStrategies.getOrDefault(gameName, defaultWeatherStrategy);
    visibilityStrategy = visibilityStrategies.getOrDefault(gameName, defaultVisibilityStrategy);
    log.debug("Weather Service received gameNameEvent, gameName set to '{}'", gameName);
  }

  private void handleWeatherEvent(final WeatherEvent weatherEvent) {
    determine();
  }

  public void determine() {
    var game = gameService.getGame();

    var currentTurn = game.getTurn();
    var currentWeather = game.getWeather();

    var weatherDto = WeatherDto.builder()
        .turn(currentTurn)
        .weather(currentWeather)
        .build();

    var visibility = visibilityStrategy.determine(currentTurn);
    var weatherType = weatherStrategy.determine(weatherDto);

    var newWeather = Weather
        .builder()
        .weatherType(weatherType)
        .visibility(visibility)
        .build();

    game.setWeather(newWeather);
  }
}
