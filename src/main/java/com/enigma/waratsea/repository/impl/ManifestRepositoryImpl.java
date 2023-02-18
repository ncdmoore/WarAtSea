package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.ManifestRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class ManifestRepositoryImpl implements ManifestRepository {
  private final GamePaths gamePaths;
  private final ResourceProvider resourceProvider;

  @Inject
  public ManifestRepositoryImpl(final GamePaths gamePaths,
                                final ResourceProvider resourceProvider) {
    this.gamePaths = gamePaths;
    this.resourceProvider = resourceProvider;
  }

  @Override
  public List<ManifestEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readManifests(filePath);
  }

  private List<ManifestEntity> readManifests(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read cargo manifests: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read cargo manifests: '{}'", filePath);
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return resourceProvider.getResourceInputStream(filePath);
  }

  private List<ManifestEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<ManifestEntity>>() {
    }.getType();

    var gson = new Gson();
    List<ManifestEntity> manifests = gson.fromJson(bufferedReader, collectionType);

    log.debug("load cargo manifests: '{}',", manifests.stream()
        .map(ManifestEntity::getId)
        .collect(Collectors.joining(",")));

    return manifests;
  }

  private FilePath getFilePath(final Side side) {
    var cargoDirectory = gamePaths.getCargoDirectory();
    var cargoFileName = gamePaths.getCargoFileName();

    return FilePath.builder()
        .baseDirectory(cargoDirectory)
        .side(side)
        .fileName(cargoFileName)
        .build();
  }
}
