package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigGameSaga implements BootStrapped {
  private final Events events;

  @Inject
  public ConfigGameSaga(final Events events) {
    this.events = events;

    events.getConfigNewGameEvent().register(this::handleConfigNewGame);
  }

  private void handleConfigNewGame(final ConfigNewGameEvent event) {
    log.info("ConfigGameSaga: handle ConfigNewGameEvent");

    events.getLoadMapEvent().fire(new LoadMapEvent());
    events.getLoadPlayerEvent().fire(new LoadPlayerEvent());

    events.getLoadSquadronEvent().fire(new LoadSquadronEvent());

  }
}
