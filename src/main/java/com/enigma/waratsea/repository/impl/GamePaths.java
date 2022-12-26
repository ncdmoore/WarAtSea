package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Scenario;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

@Slf4j
@Getter
@Singleton
public class GamePaths {
  private static final String USER_HOME = "user.home";
  private static final String DATA_DIRECTORY = "WarAtSeaData";
  private static final String SAVED_GAMES = "savedGames";

  private final String gameDirectory = "game";
  private final String cssDirectory = "css";
  private final String imageDirectory = "images";
  private final String airfieldDirectory = "airfields";
  private final String portDirectory = "ports";
  private final String commonDirectory = "common";
  private final String mapDirectory = "map";
  private final String regionDirectory = "region";
  private final String aircraftDirectory = Paths.get("aircraft", "data").toString();
  private final String scenarioDirectory = "scenarios";
  private final String userHomeDirectory = System.getProperty(USER_HOME);
  private final String regionPath = Paths.get(mapDirectory, regionDirectory).toString();

  private String gameDataDirectory;
  private String savedGameDirectory;
  private String newGameDirectory;

  private String gamePath;
  private String scenarioName;
  private String scenarioPath;

  private final String summaryFileName = "summary.json";
  private final String gameMapFileName = "map.json";
  private final String gameEntityName = "game.json";

  public String getScenarioSpecific(final String path) {
    return Paths.get(scenarioPath, path).toString();
  }

  public void setGamePath(final GameName gameName) {
    gamePath = Paths.get(gameDirectory, gameName.getValue()).toString();
    log.debug("gamePath set to: '{}'", gamePath);
  }

  public void setScenario(final Scenario scenario) {
    scenarioName = scenario.getName();
    scenarioPath = Paths.get(scenarioDirectory, scenarioName).toString();
    log.debug("scenario name: '{}'", scenarioName);
  }

  public  void setGameDataDirectoryToNewGameDirectory() {
    gameDataDirectory = newGameDirectory;

    log.debug("gameDataDirectory set to '{}'", gameDataDirectory);
  }

  public void setGameDataDirectoryToSavedGameDirectory(final Game game) {
    var savedGame = game.getId();
    gameDataDirectory = Paths.get(savedGameDirectory, savedGame).toString();

    log.debug("gameDataDirectory set to '{}'", gameDataDirectory);
  }

  public void setGameDirectories(final GameName gameName) {
    var name = gameName.getValue();
    savedGameDirectory = Paths.get(userHomeDirectory, DATA_DIRECTORY, name, SAVED_GAMES).toString();
    newGameDirectory = Paths.get(gameDirectory, name).toString();

    log.debug("DataNames received GameNameEvent, game name is: '{}'", name);
    log.debug(" - newGameDirectory set to: '{}'", newGameDirectory);
    log.debug(" - savedGameDirectory set to: '{}'", savedGameDirectory);
  }
}
