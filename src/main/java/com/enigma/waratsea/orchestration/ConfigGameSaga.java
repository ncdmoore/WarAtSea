package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.*;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.squadron.SquadronDeploymentType;
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
    var scenario = event.getScenario();
    log.info("ConfigGameSaga: handle ConfigNewGameEvent for scenario: '{}'", scenario);

    events.getLoadMapEvent().fire(new LoadMapEvent());
    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());
    events.getCreatePlayerEvent().fire(new CreatePlayerEvent());

    determineSquadronAllotment(scenario);

    events.getDeploySquadronEvent().fire(new DeploySquadronEvent());

    events.getLoadMissionsEvent().fire(new LoadMissionsEvent());
  }

  private void determineSquadronAllotment(final Scenario scenario) {
    if (scenario.getSquadron() != SquadronDeploymentType.FIXED) {
      events.getAllotSquadronEvent().fire(new AllotSquadronEvent(scenario));
    }
  }
}
