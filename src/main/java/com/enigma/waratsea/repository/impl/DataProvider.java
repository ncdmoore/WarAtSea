package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.SelectSavedGameEvent;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.exceptions.DataException;
import com.enigma.waratsea.model.Id;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Slf4j
@Singleton
public class DataProvider implements BootStrapped {
  private final GamePaths dataGamePaths;
  private boolean isNewGame = true;

  @Inject
  public DataProvider(final Events events,
                      final GamePaths dataGamePaths) {
    this.dataGamePaths = dataGamePaths;

    registerEvents(events);
  }

  public List<Path> getSubDirectoryPaths(final String directoryName) {
    var directoryPath = Paths.get(directoryName);

    createDirectory(directoryPath);

    try (var paths = Files.walk(directoryPath)) {
      return paths
          .filter(Files::isDirectory)
          .filter(Files::isReadable)
          .filter(path -> isPathSubDirectory(path, directoryName))
          .toList();
    } catch (IOException e) {
      throw new DataException("Unable to get directory paths for: " + directoryPath, e);
    }
  }

  public void createDirectoryIfNeeded(final Path directoryPath) {
    if (!Files.isDirectory(directoryPath)) {
      createDirectory(directoryPath);
    }
  }

  public InputStream getDataInputStream(final Id id, final String baseDirectory) {
    var path = getPath(id, baseDirectory);
    var fullPath = Paths.get(dataGamePaths.getGameDataDirectory(), path.toString()).toString();

    log.debug("Get data input stream for path: '{}'", fullPath);

    return isNewGame
        ? getResourceInputStream(fullPath)
        : getFileInputStream(fullPath);
  }

  public Path getSaveDirectory(final String gameId, final Id id, final String baseDirectory) {
    var savedGameDirectory = dataGamePaths.getSavedGameDirectory();
    var side = id.getSide().toLower();
    var path = Paths.get(savedGameDirectory, gameId, baseDirectory, side);

    createDirectoryIfNeeded(path);

    return path;
  }

  public InputStream getSavedFileInputStream(final Path path) throws FileNotFoundException {
    return new FileInputStream(path.toString());
  }

  public Path getSaveFile(final Path directory, final Id id) {
    var name = id.getName();
    return Paths.get(directory.toString(), name + JSON_EXTENSION);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setGameDirectories);
    events.getStartNewGameEvents().register(this::setGameDataDirectoryToNewGameDirectory);
    events.getSelectSavedGameEvent().register(this::setGameDataDirectoryToSavedGameDirectory);
  }

  private void setGameDirectories(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName();
    dataGamePaths.setGameDirectories(gameName);
  }

  private void setGameDataDirectoryToNewGameDirectory(final StartNewGameEvent startNewGameEvent) {
    isNewGame = true;
    dataGamePaths.setGameDataDirectoryToNewGameDirectory();
  }

  private void setGameDataDirectoryToSavedGameDirectory(final SelectSavedGameEvent selectSavedGameEvent) {
    isNewGame = false;
    var savedGame = selectSavedGameEvent.getGame();
    dataGamePaths.setGameDataDirectoryToSavedGameDirectory(savedGame);
  }

  private InputStream getResourceInputStream(final String fullPath) {
    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  @SneakyThrows
  private InputStream getFileInputStream(final String fullPath) {
    var path = Paths.get(fullPath);
    return Files.newInputStream(path);
  }

  private void createDirectory(final Path directoryPath) {
    try {
      Files.createDirectories(directoryPath);
    } catch (IOException e) {
      throw new DataException("Unable to create directory path: " + directoryPath, e);
    }
  }

  private boolean isPathSubDirectory(final Path path, final String parentName) {
    return Optional.ofNullable(path.getParent())
        .map(p -> p.endsWith(parentName))
        .orElseThrow(() -> new DataException("Cannot get parent directory from path: " + path));
  }

  private Path getPath(final Id id, final String baseDirectory) {
    var sidePath = id.getSide().toLower();
    var fileName = id.getName() + JSON_EXTENSION;
    return Paths.get(baseDirectory, sidePath, fileName);
  }
}
