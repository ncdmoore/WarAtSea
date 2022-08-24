package com.enigma.waratsea.service;

import com.enigma.waratsea.events.GameNameEvent;
import com.enigma.waratsea.events.NewGameEvent;
import com.enigma.waratsea.events.ScenarioEvent;
import com.enigma.waratsea.events.SideEvent;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GameService {
  private GameName gameName;

  @Getter
  private Game game;

  @Inject
  GameService(final Events events) {
    events.getGameNameEvents().register(this::setGameName);
    events.getNewGameEvents().register(this::create);
    events.getScenarioEvents().register(this::setScenario);
    events.getSideEvents().register(this::setHumanSide);
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
