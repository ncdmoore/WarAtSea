package com.enigma.waratsea.service;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.NewGameEvent;
import com.enigma.waratsea.event.ScenarioEvent;
import com.enigma.waratsea.event.SideEvent;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Creates and manages games.
 */
@Slf4j
@Singleton
public class GameService {
  private final WeatherService weatherService;
  private GameName gameName;

  @Getter
  private Game game;

  @Inject
  GameService(final Events events,
              final WeatherService weatherService) {
    events.getGameNameEvents().register(this::setGameName);
    events.getNewGameEvents().register(this::create);
    events.getScenarioEvents().register(this::setScenario);
    events.getSideEvents().register(this::setHumanSide);

    this.weatherService = weatherService;
  }

  private void nextTurn() {
    game.nextTurn();
  }

  private void determineWeather() {
    var weatherInput = WeatherService.WeatherInput
        .builder()
        .weather(game.getWeather())
        .turn(game.getTurn())
        .build();

    var newWeather = weatherService.determine(weatherInput);
    game.setWeather(newWeather);
  }

  private void setGameName(final GameNameEvent gameEvent) {
    gameName = gameEvent.getGameName();
    log.debug("Game Service received gameNameEvent, game set to: '{}'", gameEvent.getGameName());
  }

  private void create(final NewGameEvent newGameEvent) {
    game = new Game(gameName);
    log.debug("Game Service received newGameEvent");
  }

  private void setScenario(final ScenarioEvent scenarioEvent) {
    game.setScenario(scenarioEvent.getScenario());
    log.debug("Game Service received scenarioEvent, scenario set to: '{}'", scenarioEvent.getScenario().getTitle());
  }

  private void setHumanSide(final SideEvent sideEvent) {
    game.setHumanSide(sideEvent.getSide());
    log.debug("Game Service received sideEvent, side set to: '{}'", sideEvent.getSide());
  }
}
