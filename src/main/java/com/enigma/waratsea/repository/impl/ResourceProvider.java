package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.SelectScenarioEvent;
import com.enigma.waratsea.exceptions.ResourceException;
import com.enigma.waratsea.model.Id;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Slf4j
@Singleton
public class ResourceProvider implements BootStrapped {
  private final GamePaths gamePaths;

  @Inject
  public ResourceProvider(final Events events,
                          final GamePaths gamePaths) {
    this.gamePaths = gamePaths;

    registerEvents(events);
  }

  public InputStream getResourceInputStream(final Id id, final String baseDirectory) {
    var path = getPath(id, baseDirectory);

    return getResourceInputStream(path.toString());
  }

  public InputStream getResourceInputStream(final String resourcePath) {
    var scenarioSpecificPath = gamePaths.getScenarioSpecific(resourcePath);

    return Optional.ofNullable(getInputStream(scenarioSpecificPath))
        .orElseGet(() -> getInputStream(resourcePath));
  }

  public InputStream getDefaultResourceInputStream(final String resourcePath) {
    return getInputStream(resourcePath);
  }

  public List<Path> getSubDirectoryPaths(final String parentDirectoryName) {
    var fullName = Paths.get(gamePaths.getGamePath(), parentDirectoryName).toString();

    try {
      return getSubDirectoryPathsFromJar(fullName);
    } catch (URISyntaxException | IOException e) {
      throw new ResourceException("Unable to get sub directory paths for directory: " + fullName, e);
    }
  }

  private InputStream getInputStream(final String resourcePath) {
    var fullPath = Paths.get(gamePaths.getGamePath(), resourcePath).toString();

    log.debug("Get resource input stream for path: '{}'", fullPath);

    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::handleGameSelected);
    events.getSelectScenarioEvent().register(this::handleScenarioSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    gamePaths.setGamePath(gameEvent.gameName());
  }

  private void handleScenarioSelected(final SelectScenarioEvent selectScenarioEvent) {
    gamePaths.setScenario(selectScenarioEvent.getScenario());
  }

  private List<Path> getSubDirectoryPathsFromJar(final String directoryName) throws URISyntaxException, IOException {
    var jarUri = getJarPathUri();

    try (var fs = FileSystems.newFileSystem(jarUri, Collections.emptyMap());
         var paths = Files.walk(fs.getPath(directoryName))) {
      return paths
          .filter(Files::isDirectory)
          .filter(Files::isReadable)
          .filter(path -> isPathSubDirectory(path, directoryName))
          .toList();
    }
  }

  private URI getJarPathUri() throws URISyntaxException {
    var jarPath = getClass().getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath();

    return URI.create("jar:file:" + jarPath);
  }

  private boolean isPathSubDirectory(final Path path, final String parentName) {
    return Optional.ofNullable(path.getParent())
        .map(p -> p.endsWith(parentName))
        .orElseThrow(() -> new ResourceException("Unable to get parent path of path: " + path));
  }

  private Path getPath(final Id id, final String baseDirectory) {
    var sidePath = id.getSide().toLower();
    var fileName = id.getName() + JSON_EXTENSION;
    return Paths.get(baseDirectory, sidePath, fileName);
  }
}
