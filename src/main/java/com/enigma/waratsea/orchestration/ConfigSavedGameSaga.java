package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.ConfigSavedGameEvent;
import com.enigma.waratsea.event.CreatePlayerEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.event.LoadMissionsEvent;
import com.enigma.waratsea.event.LoadSquadronsEvent;
import com.enigma.waratsea.event.LoadTaskForcesEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigSavedGameSaga implements BootStrapped {
  private final Events events;

  @Inject
  public ConfigSavedGameSaga(final Events events) {
    this.events = events;

    events.getConfigSavedGameEvent().register(this::handleConfigSavedGame);
  }

  private void handleConfigSavedGame(final ConfigSavedGameEvent event) {
    var game = event.getGame();
    log.info("ConfigSavedGameSaga: handle ConfigNewGameEvent for scenario: '{}'", game);

    loadMap();
    loadTaskForces();
    loadSquadrons();
    loadMissions();
    createPlayers();
  }

  private void loadMap() {
    events.getLoadMapEvent().fire(new LoadMapEvent());
  }

  private void loadTaskForces() {
    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());
  }

  private void loadSquadrons() {
    events.getLoadSquadronsEvent().fire(new LoadSquadronsEvent());
  }

  private void loadMissions() {
    events.getLoadMissionsEvent().fire(new LoadMissionsEvent());
  }

  private void createPlayers() {
    events.getCreatePlayerEvent().fire(new CreatePlayerEvent());
  }
}
