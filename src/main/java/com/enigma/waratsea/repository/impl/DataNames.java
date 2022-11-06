package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.GAME_DIRECTORY;

@Slf4j
@Singleton
public class DataNames {
  private static final String USER_HOME = "user.home";
  private static final String DATA_DIRECTORY = "WarAtSeaData";
  private static final String SAVED_GAMES = "savedGames";

  private final String userHomeDirectory;
  private String newGameDirectory;

  @Getter
  private String gameDataDirectory;

  @Getter
  private String savedGameDirectory;

  @Getter
  private final String gameEntityName = "game.json";

  @Getter
  private final String airfieldDirectory = "airfields";

  @Getter
  private final String portDirectory = "ports";

  @Inject
  public DataNames() {
    this.userHomeDirectory = System.getProperty(USER_HOME);
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
    newGameDirectory = Paths.get(GAME_DIRECTORY, name).toString();

    log.debug("DataNames received GameNameEvent, game name is: '{}'", name);
    log.debug(" - newGameDirectory set to: '{}'", newGameDirectory);
    log.debug(" - savedGameDirectory set to: '{}'", savedGameDirectory);
  }
}
