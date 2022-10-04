package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.ConfigGameEvent;
import com.enigma.waratsea.model.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigGameSaga implements BootStrapped {
  @Inject
  public ConfigGameSaga(final Events events) {
    events.getConfigGameEvent().register(this::handleConfigGame);
  }

  private void handleConfigGame(final ConfigGameEvent event) {
    log.info("ConfigGameSaga: handle config game event");
  }
}
