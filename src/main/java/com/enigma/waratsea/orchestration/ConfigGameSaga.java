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

    loadMap();
    loadTaskForces();
    determineSquadronAllotment(scenario);
    deploySquadrons();
    loadMissions();
    createPlayers();
  }

  private void loadMap() {
    events.getLoadMapEvent().fire(new LoadMapEvent());
  }

  private void loadTaskForces() {
    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());
  }

  private void createPlayers() {
    events.getCreatePlayerEvent().fire(new CreatePlayerEvent());
  }

  private void determineSquadronAllotment(final Scenario scenario) {
    if (scenario.getSquadron() != SquadronDeploymentType.FIXED) {
      events.getAllotSquadronEvent().fire(new AllotSquadronEvent(scenario));
    }
  }

  private void deploySquadrons() {
    events.getDeploySquadronEvent().fire(new DeploySquadronEvent());
  }

  private void loadMissions() {
    events.getLoadMissionsEvent().fire(new LoadMissionsEvent());
  }
}
