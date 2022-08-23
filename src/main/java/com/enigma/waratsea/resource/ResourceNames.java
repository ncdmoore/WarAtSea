package com.enigma.waratsea.resource;

import com.enigma.waratsea.events.GameNameEvent;
import com.enigma.waratsea.events.ScenarioEvent;
import com.enigma.waratsea.model.GlobalEvents;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@Getter
public class ResourceNames {
  private String gameName;
  private String scenarioName;
  private final String gameDirectory = "game";
  private final String cssDirectory = "css";
  private final String imageDirectory = "images";
  private final String scenarioDirectory = "scenarios";
  private final String summaryFileName = "summary.json";

  @Inject
  public ResourceNames(final GlobalEvents globalEvents) {
    globalEvents.getGameNameEvents().register(this::handleGameSelected);
    globalEvents.getScenarioEvents().register(this::handleScenarioSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    gameName = gameEvent.getGameName().getValue();
    log.debug("ResourceNames received gameNameEvent, game set to: '{}'", gameName);
  }

  private void handleScenarioSelected(final ScenarioEvent scenarioEvent) {
    scenarioName = scenarioEvent.getScenario().getName();
    log.debug("ResourceNames received scenarioEvent, scenario name: '{}'", scenarioName);
  }
}
