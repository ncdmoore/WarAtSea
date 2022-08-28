package com.enigma.waratsea.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class DataProvider {
  private final DataNames dataNames;

  @Inject
  public DataProvider(final DataNames dataNames) {
    this.dataNames = dataNames;
  }

  public List<Path> getSubDirectoryPaths(final String directoryName) {
    var directoryPath = Paths.get(directoryName);

    createDirectory(directoryName);

    try (var paths = Files.walk(directoryPath)) {
      return paths
          .filter(Files::isDirectory)
          .filter(Files::isReadable)
          .filter(path -> isPathSubDirectory(path, directoryName))
          .collect(Collectors.toList());
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }

  @SneakyThrows
  public void createDirectory(final Path directoryPath) {
    Files.createDirectories(directoryPath);
  }

  @SneakyThrows
  private void createDirectory(final String directoryName) {
    Files.createDirectories(Paths.get(directoryName));
  }

  private boolean isPathSubDirectory(final Path path, final String parentName) {
    return path.getParent().endsWith(parentName);
  }
}
