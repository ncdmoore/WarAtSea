package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.exceptions.DataException;
import com.enigma.waratsea.model.Id;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

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
public class DataProvider {
  private final DataNames dataNames;

  @Inject
  public DataProvider(final DataNames dataNames) {
    this.dataNames = dataNames;
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
    return getDataInputStream(path.toString());
  }

  public InputStream getDataInputStream(final String relativeDataPath) {
    var fullPath = Paths.get(dataNames.getGameDataDirectory(), relativeDataPath).toString();

    log.debug("Get data input stream for path: '{}'", fullPath);

    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  public Path getSaveDirectory(final String gameId, final Id id, final String baseDirectory) {
    var savedGameDirectory = dataNames.getSavedGameDirectory();
    var side = id.getSide().toLower();
    var path = Paths.get(savedGameDirectory, gameId, baseDirectory, side);

    createDirectoryIfNeeded(path);

    return path;
  }

  public Path getSaveFile(final Path directory, final Id id) {
    var name = id.getName();
    return Paths.get(directory.toString(), name + JSON_EXTENSION);
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
