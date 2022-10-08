package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.ConfigGameEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.event.LoadPlayerEvent;
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

    events.getConfigGameEvent().register(this::handleConfigGame);
  }

  private void handleConfigGame(final ConfigGameEvent event) {
    log.info("ConfigGameSaga: handle config game event");

    events.getLoadMapEvent().fire(new LoadMapEvent());
    events.getLoadPlayerEvent().fire(new LoadPlayerEvent());
  }
}
