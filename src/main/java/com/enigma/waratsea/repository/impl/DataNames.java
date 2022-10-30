package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.event.*;
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

  @Inject
  public DataNames(final Events events) {
    this.userHomeDirectory = System.getProperty(USER_HOME);

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setGameDirectories);
    events.getStartNewGameEvents().register(this::setGameDataDirectoryToNewGameDirectory);
    events.getSelectSavedGameEvent().register(this::setGameDataDirectoryToSavedGameDirectory);
  }

  private void setGameDirectories(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName().getValue();
    savedGameDirectory = Paths.get(userHomeDirectory, DATA_DIRECTORY, gameName, SAVED_GAMES).toString();
    newGameDirectory = Paths.get(GAME_DIRECTORY, gameName).toString();

    log.debug("DataNames received GameNameEvent, game name is: '{}'", gameName);
    log.debug(" - newGameDirectory set to: '{}'", newGameDirectory);
    log.debug(" - savedGameDirectory set to: '{}'", savedGameDirectory);
  }

  private void setGameDataDirectoryToNewGameDirectory(final StartNewGameEvent startNewGameEvent) {
    gameDataDirectory = newGameDirectory;

    log.debug("DataNames received StartNewGameEvent, gameDataDirectory set to '{}'", gameDataDirectory);
  }

  private void setGameDataDirectoryToSavedGameDirectory(final SelectSavedGameEvent selectSavedGameEvent) {
    var savedGame = selectSavedGameEvent.getGame().getId();
    gameDataDirectory = Paths.get(savedGameDirectory, savedGame).toString();

    log.debug("DataNames received SelectSavedGameEvent, gameDataDirectory set to '{}'", gameDataDirectory);
  }
}
