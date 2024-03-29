package com.enigma.waratsea.orchestration;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.AllotSquadronEvent;
import com.enigma.waratsea.event.ConfigNewGameEvent;
import com.enigma.waratsea.event.CreatePlayerEvent;
import com.enigma.waratsea.event.DeploySquadronEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadCargoEvent;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.event.LoadTaskForcesEvent;
import com.enigma.waratsea.model.Scenario;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigNewGameSaga implements BootStrapped {
  private final Events events;

  @Inject
  public ConfigNewGameSaga(final Events events) {
    this.events = events;

    events.getConfigNewGameEvent().register(this::handleConfigNewGame);
  }

  private void handleConfigNewGame(final ConfigNewGameEvent event) {
    var scenario = event.getScenario();
    log.info("ConfigNewGameSaga: handle ConfigNewGameEvent for scenario: '{}'", scenario);

    loadMap();
    loadTaskForces();
    determineSquadronAllotment(scenario);
    deploySquadrons();
    loadCargo();
    createPlayers();
  }

  private void loadMap() {
    events.getLoadMapEvent().fire(new LoadMapEvent());
  }

  private void loadTaskForces() {
    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());
  }

  private void determineSquadronAllotment(final Scenario scenario) {
    if (scenario.hasAllotments()) {
      events.getAllotSquadronEvent().fire(new AllotSquadronEvent(scenario));
    }
  }

  private void deploySquadrons() {
    events.getDeploySquadronEvent().fire(new DeploySquadronEvent());
  }

  private void loadCargo() {
    events.getLoadCargoEvent().fire(new LoadCargoEvent());
  }

  private void createPlayers() {
    events.getCreatePlayerEvent().fire(new CreatePlayerEvent());
  }
}
