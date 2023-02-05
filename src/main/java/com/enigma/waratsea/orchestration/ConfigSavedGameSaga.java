package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.*;
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
    loadMissions();
    createPlayers();
  }

  private void loadMap() {
    events.getLoadMapEvent().fire(new LoadMapEvent());
  }

  private void loadTaskForces() {
    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());
  }

  private void loadMissions() {
    events.getLoadMissionsEvent().fire(new LoadMissionsEvent());
  }

  private void createPlayers() {
    events.getCreatePlayerEvent().fire(new CreatePlayerEvent());
  }
}