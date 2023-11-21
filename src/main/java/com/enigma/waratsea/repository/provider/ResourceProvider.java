package com.enigma.waratsea.repository.provider;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.user.SelectSavedGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.exception.ResourceException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class ResourceProvider implements BootStrapped {
  private final GamePaths gamePaths;
  private final Resource resource;

  @Inject
  public ResourceProvider(final Events events,
                          final GamePaths gamePaths,
                          final Resource resource) {
    this.gamePaths = gamePaths;
    this.resource = resource;

    registerEvents(events);
  }

  public List<Path> getSubDirectoryPaths(final String parentDirectoryName) {
    var fullName = Paths.get(gamePaths.getGamePath(), parentDirectoryName).toString();

    try {
      return getSubDirectoryPathsFromJar(fullName);
    } catch (URISyntaxException | IOException e) {
      throw new ResourceException("Unable to get sub directory paths for directory: " + fullName, e);
    }
  }

  public InputStream getInputStream(final FilePath filePath) {
    var path = filePath.getPath();

    return getResourceInputStream(path);
  }

  public InputStream getDefaultInputStream(final FilePath filePath) {
    var path = filePath.getPath();
    var fullPath = Paths.get(gamePaths.getGamePath(), path).toString();

    return resource.getInputStream(fullPath);
  }

  public InputStream getDefaultInputStream(final String resourcePath) {
    var fullPath = Paths.get(gamePaths.getGamePath(), resourcePath).toString();

    return resource.getInputStream(fullPath);
  }

  private InputStream getResourceInputStream(final String resourcePath) {
    var gamePath = gamePaths.getGamePath();
    var scenarioPath = gamePaths.getScenarioPath();
    var scenarioSpecificResourcePath = Optional.ofNullable(scenarioPath)
        .map(sp -> Paths.get(sp, resourcePath).toString())
        .orElse(resourcePath);

    scenarioSpecificResourcePath = Paths.get(gamePath, scenarioSpecificResourcePath).toString();

    var defaultResourcePath = Paths.get(gamePath, resourcePath).toString();

    return Optional.ofNullable(resource.getInputStream(scenarioSpecificResourcePath))
        .orElseGet(() -> resource.getInputStream(defaultResourcePath));
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvent().register(this::handleGameSelected);
    events.getSelectScenarioEvent().register(this::handleScenarioSelected);
    events.getSelectSavedGameEvent().register(this::handleSavedGameSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    gamePaths.setGamePath(gameEvent.gameName());
  }

  private void handleScenarioSelected(final SelectScenarioEvent selectScenarioEvent) {
    gamePaths.setScenario(selectScenarioEvent.getScenario());
  }

  private void handleSavedGameSelected(final SelectSavedGameEvent selectSavedGameEvent) {
    gamePaths.setScenario(selectSavedGameEvent.getGame().getScenario());
  }

  private List<Path> getSubDirectoryPathsFromJar(final String directoryName) throws URISyntaxException, IOException {
    var jarUri = resource.getUri();

    try (var fs = FileSystems.newFileSystem(jarUri, Collections.emptyMap());
         var paths = Files.walk(fs.getPath(directoryName))) {
      return paths
          .filter(Files::isDirectory)
          .filter(Files::isReadable)
          .filter(path -> isPathSubDirectory(path, directoryName))
          .toList();
    }
  }

  private boolean isPathSubDirectory(final Path path, final String parentName) {
    return Optional.ofNullable(path.getParent())
        .map(p -> p.endsWith(parentName))
        .orElseThrow(() -> new ResourceException("Unable to get parent path of path: " + path));
  }
}
