package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.SelectSavedGameEvent;
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
@Singleton
public class DataNames {
  private static final String USER_HOME = "user.home";
  private static final String DATA_DIRECTORY = "WarAtSeaData";
  private static final String SAVED_GAMES = "savedGames";

  private final String userHomeDirectory;

  @Getter
  private String gameDataDirectory;

  @Getter
  private String savedGameDirectory;

  @Getter
  private final String gameEntityName = "game.json";

  private String newGameDirectory;

  @Inject
  public DataNames(final Events events) {
    this.userHomeDirectory = System.getProperty(USER_HOME);

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setSavedGameDirectory);
    events.getSelectScenarioEvent().register(this::setScenario);
    events.getSelectSavedGameEvent().register(this::setSavedGame);
  }

  private void setSavedGameDirectory(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName().getValue();
    savedGameDirectory = Paths.get(userHomeDirectory, DATA_DIRECTORY, gameName, SAVED_GAMES).toString();
    newGameDirectory = Paths.get(GAME_DIRECTORY, gameName, SCENARIO_DIRECTORY).toString();

    log.debug("DataNames received game name event, newGameDirectory set to '{}'", newGameDirectory);
    log.debug("DataNames received game name event, savedGameDirectory set to '{}'", savedGameDirectory);
  }

  private void setScenario(final SelectScenarioEvent selectScenarioEvent) {
    var scenario = selectScenarioEvent.getScenario().getName();
    gameDataDirectory = Paths.get(newGameDirectory, scenario).toString();

    log.debug("DataNames received selectScenarioEvent, gameDataDirectory set to '{}'", gameDataDirectory);
  }

  private void setSavedGame(final SelectSavedGameEvent selectSavedGameEvent) {
    var savedGame = selectSavedGameEvent.getGame().getId();
    gameDataDirectory = Paths.get(savedGameDirectory, savedGame).toString();

    log.debug("DataNames received selectSavedGameEvent, gameDataDirectory set to '{}'", gameDataDirectory);
  }
}
