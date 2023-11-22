package com.enigma.waratsea.repository.provider;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.exception.DataException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Singleton
public class PreferencesProvider implements BootStrapped {
  private final GamePaths gamePaths;
  private final Resource resource;

  @Inject
  public PreferencesProvider(final Events events,
                             final GamePaths gamePaths,
                             final Resource resource) {
    this.gamePaths = gamePaths;
    this.resource = resource;

    registerEvents(events);
  }

  public InputStream getInputStream() {
    return doesFileExist()
        ? getDataFileInputStream()
        : getResourceFileInputStream();
  }

  public OutputStream getOutputStream() throws FileNotFoundException {
    var savedGameDirectoryPath = Paths.get(gamePaths.getSavedGameDirectory());

    createDirectoryIfNeeded(savedGameDirectoryPath);

    var savedPreferencesFilePath = gamePaths.getSavedPreferencesFile();

    log.info("save preferences file: '{}'", savedPreferencesFilePath);

    return new FileOutputStream(savedPreferencesFilePath);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvent().register(this::setPreferencesFile);
  }

  private void setPreferencesFile(final GameNameEvent gameNameEvent) {
    var gameName = gameNameEvent.gameName();
    gamePaths.setPreferencesFiles(gameName);
  }

  private boolean doesFileExist() {
    var file = new File(gamePaths.getSavedPreferencesFile());

    return file.exists();
  }

  private InputStream getResourceFileInputStream() {
    var fullPath = gamePaths.getDefaultPreferencesFile();

    log.info("get default preferences file: '{}'", fullPath);

    return resource.getInputStream(fullPath);
  }

  @SneakyThrows
  private InputStream getDataFileInputStream() {
    var fullPath = gamePaths.getSavedPreferencesFile();
    var path = Paths.get(fullPath);

    log.info("get saved preferences file: '{}'", fullPath);

    return Files.newInputStream(path);
  }

  private void createDirectoryIfNeeded(final Path directoryPath) {
    if (!Files.isDirectory(directoryPath)) {
      createDirectory(directoryPath);
    }
  }

  private void createDirectory(final Path directoryPath) {
    try {
      Files.createDirectories(directoryPath);
    } catch (IOException e) {
      throw new DataException("Unable to create directory path: " + directoryPath, e);
    }
  }
}
