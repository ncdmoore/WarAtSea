package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.ConfigApplicationEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.LoadRegistryEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigApplicationSaga implements BootStrapped {
  private final Events events;

  @Inject
  public ConfigApplicationSaga(final Events events) {
    this.events = events;

    events.getConfigApplicationEvent().register(this::handleConfigApplicationEvent);
  }

  private void handleConfigApplicationEvent(final ConfigApplicationEvent configApplicationEvent) {
    log.info("ConfigApplicationSaga: handle ConfigApplicationEvent");

    var gameName = configApplicationEvent.gameName();
    events.getGameNameEvent().fire(new GameNameEvent(gameName));
    events.getLoadRegistryEvent().fire(new LoadRegistryEvent());
  }

}
