package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.ScenarioEvent;
import com.enigma.waratsea.model.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

@Slf4j
@Singleton
@Getter
public class ResourceNames {
  private final String game ="game";
  private String gamePath;
  private String scenarioName;
  private final String commonDirectory = "common";
  private final String cssDirectory = "css";
  private final String imageDirectory = "images";
  private final String mapDirectory = "map";
  private final String scenarioDirectory = "scenarios";
  private final String summaryFileName = "summary.json";
  private final String gameMapFileName = "map.json";

  @Inject
  ResourceNames(final Events events) {
    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::handleGameSelected);
    events.getScenarioEvents().register(this::handleScenarioSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    gamePath = Paths.get(game, gameEvent.gameName().getValue()).toString();
    log.debug("ResourceNames received gameNameEvent, gamePath set to: '{}'", gamePath);
  }

  private void handleScenarioSelected(final ScenarioEvent scenarioEvent) {
    scenarioName = scenarioEvent.getScenario().getName();
    log.debug("ResourceNames received scenarioEvent, scenario name: '{}'", scenarioName);
  }
}
