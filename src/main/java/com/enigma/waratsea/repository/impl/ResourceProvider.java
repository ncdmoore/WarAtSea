package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.SelectScenarioEvent;
import com.enigma.waratsea.exceptions.ResourceException;
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

@Slf4j
@Singleton
public class ResourceProvider implements BootStrapped {
  private final ResourceNames resourceNames;

  @Inject
  public ResourceProvider(final Events events,
                          final ResourceNames resourceNames) {
    this.resourceNames = resourceNames;

    registerEvents(events);
  }

  public InputStream getResourceInputStream(final String resourcePath) {
    var scenarioSpecificPath = resourceNames.getScenarioSpecific(resourcePath);

    return Optional.ofNullable(getInputStream(scenarioSpecificPath))
        .orElseGet(() -> getInputStream(resourcePath));
  }

  public InputStream getDefaultResourceInputStream(final String resourcePath) {
    return getInputStream(resourcePath);
  }

  private InputStream getInputStream(final String resourcePath) {
    var fullPath = Paths.get(resourceNames.getGamePath(), resourcePath).toString();

    log.debug("Get resource input stream for path: '{}'", fullPath);

    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  public List<Path> getSubDirectoryPaths(final String parentDirectoryName) {
    var fullName = Paths.get(resourceNames.getGamePath(), parentDirectoryName).toString();

    try {
      return getSubDirectoryPathsFromJar(fullName);
    } catch (URISyntaxException | IOException e) {
      throw new ResourceException("Unable to get sub directory paths for directory: " + fullName, e);
    }
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::handleGameSelected);
    events.getSelectScenarioEvent().register(this::handleScenarioSelected);
  }

  private void handleGameSelected(final GameNameEvent gameEvent) {
    resourceNames.setGamePath(gameEvent.gameName());
  }

  private void handleScenarioSelected(final SelectScenarioEvent selectScenarioEvent) {
    resourceNames.setScenario(selectScenarioEvent.getScenario());
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
}
