package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.user.SelectSavedGameEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.exception.DataException;
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
  private final GamePaths gamePaths;
  private boolean isNewGame = true;

  @Inject
  public DataProvider(final Events events,
                      final GamePaths gamePaths) {
    this.gamePaths = gamePaths;

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

  public InputStream getDataInputStream(final FilePath filePath) {
    var gameDataDirectory = gamePaths.getGameDataDirectory();
    var scenarioDirectory = gamePaths.getScenarioPath();
    var path = filePath.getPath();

    var genericFullPath = Paths.get(gameDataDirectory, path).toString();
    var scenarioSpecificFullPath = Optional.ofNullable(scenarioDirectory)
        .map(sd -> Paths.get(gameDataDirectory, sd, path).toString())
        .orElse(genericFullPath);

    var inputStream = isNewGame
        ? getResourceInputStream(scenarioSpecificFullPath, genericFullPath)
        : getFileInputStream(genericFullPath);

    if (inputStream == null) {
      log.warn("Cannot find scenario specific path: '{}'", scenarioSpecificFullPath);
      log.warn("Cannot find generic path: '{}'", genericFullPath);
    }

    return inputStream;
  }

  public Path getSaveDirectory(final String gameId) {
    var savedGameDirectory = gamePaths.getSavedGameDirectory();
    var path = Paths.get(savedGameDirectory, gameId);

    createDirectoryIfNeeded(path);

    return path;
  }

  public InputStream getSavedFileInputStream(final Path path) throws FileNotFoundException {
    return new FileInputStream(path.toString());
  }

  public Path getSaveFile(final String gameId, final FilePath filePath) {
    var path = getSavedEntityDirectory(gameId, filePath);
    var name = filePath.getFileName();
    return Paths.get(path.toString(), name + JSON_EXTENSION);
  }

  private Path getSavedEntityDirectory(final String gameId, final FilePath filePath) {
    var savedGameDirectory = gamePaths.getSavedGameDirectory();
    var side = filePath.getSide().toLower();
    var baseDirectory = filePath.getBaseDirectory();
    var path = Paths.get(savedGameDirectory, gameId, baseDirectory, side);

    createDirectoryIfNeeded(path);

    return path;
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvent().register(this::setGameDirectories);
    events.getStartNewGameEvent().register(this::setGameDataDirectoryToNewGameDirectory);
    events.getSelectSavedGameEvent().register(this::setGameDataDirectoryToSavedGameDirectory);
  }

  private void setGameDirectories(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName();
    gamePaths.setGameDirectories(gameName);
  }

  private void setGameDataDirectoryToNewGameDirectory(final StartNewGameEvent startNewGameEvent) {
    isNewGame = true;
    gamePaths.setGameDataDirectoryToNewGameDirectory();
  }

  private void setGameDataDirectoryToSavedGameDirectory(final SelectSavedGameEvent selectSavedGameEvent) {
    isNewGame = false;
    var savedGame = selectSavedGameEvent.getGame();
    gamePaths.setGameDataDirectoryToSavedGameDirectory(savedGame);
  }

  private void createDirectoryIfNeeded(final Path directoryPath) {
    if (!Files.isDirectory(directoryPath)) {
      createDirectory(directoryPath);
    }
  }

  private InputStream getResourceInputStream(final String scenarioSpecificPath, final String genericPath) {
    return Optional.ofNullable(getResourceInputStream(scenarioSpecificPath))
        .orElseGet(() -> getResourceInputStream(genericPath));
  }

  private InputStream getResourceInputStream(final String fullPath) {
    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  @SneakyThrows
  private InputStream getFileInputStream(final String fullPath) {
    log.debug("Get data input stream for path: '{}'", fullPath);

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
}
