package com.enigma.waratsea.data;

import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;

@Slf4j
@Singleton
public class DataNames {
  private static final String USER_HOME = "user.home";
  private static final String DATA_DIRECTORY = "WarAtSeaData";
  private static final String SAVED_GAMES = "savedGames";

  private final String userHomeDirectory;
  private final ResourceNames resourceNames;

  @Getter
  private String savedGameDirectory;

  @Getter
  private final String gameEntityName = "game.json";

  @Inject
  public DataNames(final Events events,
                   final ResourceNames resourceNames) {
    this.resourceNames = resourceNames;
    this.userHomeDirectory = System.getProperty(USER_HOME);

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setSavedGameDirectory);
  }

  private void setSavedGameDirectory(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName().getValue();
    savedGameDirectory = Paths.get(userHomeDirectory, DATA_DIRECTORY, gameName, SAVED_GAMES).toString();
    log.debug("DataNames received game name event, savedGameDirectory set to '{}'", savedGameDirectory);
  }
}
