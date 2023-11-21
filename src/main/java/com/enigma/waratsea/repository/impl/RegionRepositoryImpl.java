package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class RegionRepositoryImpl implements RegionRepository {
  private final GamePaths gamePaths;
  private final ResourceProvider resourceProvider;

  @Override
  public List<RegionEntity> get(final Id mapId) {
    var filePath = getFilePath(mapId);

    return createRegions(filePath);
  }

  private List<RegionEntity> createRegions(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read regions for map: '{}'", filePath);
      return readRegions(br);
    } catch (IOException e) {
      throw new GameException("Unable to create regions: " + filePath);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return resourceProvider.getInputStream(filePath);
  }

  private List<RegionEntity> readRegions(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<RegionEntity>>() { }.getType();

    Gson gson = new Gson();
    List<RegionEntity> regions = gson.fromJson(bufferedReader, collectionType);

    log.info("load regions: {}", regions.stream().map(this::getRegionId).collect(Collectors.joining(",")));

    return regions;
  }

  private String getRegionId(final RegionEntity regionEntity) {
    return regionEntity.getName() + ":" + regionEntity.getNation();
  }

  private FilePath getFilePath(final Id mapId) {
    var regionDirectory = gamePaths.getRegionPath();

    return FilePath.builder()
        .baseDirectory(regionDirectory)
        .side(mapId.getSide())
        .fileName(mapId.getName())
        .build();
  }
}
