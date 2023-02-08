package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.ApplyAllotmentModEvent;
import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.ConfigScenarioOptionsEvent;
import com.enigma.waratsea.event.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigScenarioOptionsSaga implements BootStrapped {
  private final Events events;

  @Inject
  public ConfigScenarioOptionsSaga(final Events events) {
    this.events = events;

    events.getConfigScenarioOptionsEvent().register(this::handleConfigScenarioOptions);
  }

  private void handleConfigScenarioOptions(final ConfigScenarioOptionsEvent configScenarioOptionsEvent) {
    var modifications = configScenarioOptionsEvent.getAllotmentModifications();

    events.getClearEvent().fire(new ClearEvent());
    events.getApplyAllotmentModEvent().fire(new ApplyAllotmentModEvent(modifications));
  }
}
