package com.enigma.waratsea.repository.provider;

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
  private final String aircraftBaseDirectory = "aircraft";
  private final String shipBaseDirectory = "ships";
  private final String aircraftDirectory = Paths.get(aircraftBaseDirectory, "data").toString();
  private final String squadronDirectory = Paths.get("squadrons", "data").toString();
  private final String squadronAllotmentDirectory = Paths.get("squadrons", "allotment").toString();
  private final String squadronDeploymentDirectory = Paths.get("squadrons", "deployment").toString();
  private final String squadronManifestFileName = "manifest";
  private final String scenarioDirectory = "scenarios";
  private final String shipRegistryDirectory = Paths.get(shipBaseDirectory, "registry").toString();
  private final String shipDirectory = Paths.get(shipBaseDirectory, "data").toString();
  private final String cargoDirectory = "cargo";
  private final String cargoFileName = "manifests";
  private final String taskForceDirectory = Paths.get("taskForces", "data").toString();
  private final String flotillaDirectory = Paths.get("flotillas", "data").toString();
  private final String missionDirectory = "missions";
  private final String missionFileName = "missions";
  private final String victoryDirectory = "victory";
  private final String victoryFileName = "victory";
  private final String releaseDirectory = "releases";
  private final String releaseFileName = "releases";
  private final String regionPath = Paths.get(mapDirectory, regionDirectory).toString();

  private final String squadronAllotmentModDirectory = Paths.get("options", "allotment").toString();

  private final String userHomeDirectory = System.getProperty(USER_HOME);

  private String gameDataDirectory;
  private String savedGameDirectory;
  private String newGameDirectory;

  private String defaultPreferencesFile;
  private String savedPreferencesFile;

  private String gamePath;
  private String scenarioName;
  private String scenarioPath;

  private final String preferencesFileName = "preferences.json";
  private final String summaryFileName = "summary.json";
  private final String gameMapFileName = "map.json";
  private final String gameEntityName = "game.json";

  private final String squadronDeploymentFileName = "deployment";
  private final String taskForceFileName = "taskForces";
  private final String submarineFileName = "submarines";
  private final String mtbFileName = "mtbs";

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
    gameDataDirectory = newGameDirectory; // assume new game at boot time.

    log.debug("newGameDirectory set to: '{}'", newGameDirectory);
    log.debug("savedGameDirectory set to: '{}'", savedGameDirectory);
  }

  public void setPreferencesFiles(final GameName gameName) {
    var name = gameName.getValue();
    defaultPreferencesFile = Paths.get(gameDirectory, name, preferencesFileName).toString();
    savedPreferencesFile = Paths.get(userHomeDirectory, DATA_DIRECTORY, name, preferencesFileName).toString();

    log.info("defaultPreferencesFile set to: '{}'", defaultPreferencesFile);
    log.info("savedPreferencesFile set to: '{}'", savedPreferencesFile);
  }
}
