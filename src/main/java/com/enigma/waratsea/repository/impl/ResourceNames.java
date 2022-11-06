package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Scenario;
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


  public String getScenarioSpecific(final String path) {
    return Paths.get(scenarioPath, path).toString();
  }

  public void setGamePath(final GameName gameName) {
    gamePath = Paths.get(game, gameName.getValue()).toString();
    log.debug("gamePath set to: '{}'", gamePath);
  }

  public void setScenario(final Scenario scenario) {
    scenarioName = scenario.getName();
    scenarioPath = Paths.get(scenarioDirectory, scenarioName).toString();
    log.debug("scenario name: '{}'", scenarioName);
  }
}
