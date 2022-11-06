package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.SelectScenarioEvent;
import com.enigma.waratsea.event.Events;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.GAME_DIRECTORY;
import static com.enigma.waratsea.Constants.SCENARIO_DIRECTORY;

@Slf4j
@Getter
@Singleton
public class ResourceNames {
  private final String game = GAME_DIRECTORY;
  private String gamePath;
  private String scenarioName;
  private String scenarioPath;
  private final String commonDirectory = "common";
  private final String cssDirectory = "css";
  private final String imageDirectory = "images";
  private final String mapDirectory = "map";
  private final String regionDirectory = "region";
  private final String scenarioDirectory = SCENARIO_DIRECTORY;
  private final String summaryFileName = "summary.json";
  private final String gameMapFileName = "map.json";
  private final String regionPath = Paths.get(mapDirectory, regionDirectory).toString();

  @Inject
  ResourceNames(final Events events) {
    registerEvents(events);
  }

  public String getScenarioSpecific(final String path) {
    return Paths.get(scenarioPath, path).toString();
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::handleGameSelected);
    events.getSelectScenarioEvent().register(this::handleScenarioSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    gamePath = Paths.get(game, gameEvent.gameName().getValue()).toString();
    log.debug("ResourceNames received gameNameEvent, gamePath set to: '{}'", gamePath);
  }

  private void handleScenarioSelected(final SelectScenarioEvent selectScenarioEvent) {
    scenarioName = selectScenarioEvent.getScenario().getName();
    scenarioPath = Paths.get(scenarioDirectory, scenarioName).toString();
    log.debug("ResourceNames received scenarioEvent, scenario name: '{}'", scenarioName);
  }
}
