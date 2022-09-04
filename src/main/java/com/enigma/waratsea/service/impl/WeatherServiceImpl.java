package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.WeatherInput;
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

  @Inject
  public WeatherServiceImpl(final Events events,
                            final @Named("Default") WeatherStrategy defaultWeatherStrategy,
                            final @Named("Default") VisibilityStrategy defaultVisibilityStrategy,
                            final Map<GameName, WeatherStrategy> weatherStrategies,
                            final Map<GameName, VisibilityStrategy> visibilityStrategies) {
    this.defaultWeatherStrategy = defaultWeatherStrategy;
    this.defaultVisibilityStrategy = defaultVisibilityStrategy;
    this.weatherStrategies = weatherStrategies;
    this.visibilityStrategies = visibilityStrategies;

    visibilityStrategy = defaultVisibilityStrategy;
    weatherStrategy = defaultWeatherStrategy;

    registerEvents(events);
  }

  @Override
  public Weather determine(WeatherInput input) {
    var visibility = visibilityStrategy.determine(input.getTurn());
    var weatherType = weatherStrategy.determine(input);

    return Weather
        .builder()
        .weatherType(weatherType)
        .visibility(visibility)
        .build();
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setGameName);
  }

  private void setGameName(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName();

    weatherStrategy = weatherStrategies.getOrDefault(gameName, defaultWeatherStrategy);
    visibilityStrategy = visibilityStrategies.getOrDefault(gameName, defaultVisibilityStrategy);
    log.debug("Weather Service received gameNameEvent, gameName set to '{}'", gameName);
  }
}
