package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.RegionRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Slf4j
@Singleton
public class RegionRepositoryImpl implements RegionRepository {
  private final ResourceNames resourceNames;
  private final ResourceProvider resourceProvider;

  @Inject
  public RegionRepositoryImpl(final ResourceNames resourceNames,
                              final ResourceProvider resourceProvider) {
    this.resourceNames = resourceNames;
    this.resourceProvider = resourceProvider;
  }

  @Override
  public List<RegionEntity> get(final Side side, final String regionName) {
    return createRegions(side, regionName);
  }

  private List<RegionEntity> createRegions(final Side side, final String regionName) {
    try (var in = getRegionInputStream(side, regionName);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.info("Read regions for side: '{}', map: '{}'", side, regionName);
      return readRegions(br);
    } catch (IOException e) {
      throw new GameException("Unable to create regions: " + regionName + " for side: " + side);
    }
  }

  private InputStream getRegionInputStream(final Side side, final String regionName) {
    var sidePath = side.toLower();
    var fileName = regionName + JSON_EXTENSION;
    var regionBasePath = resourceNames.getRegionPath();
    var defaultRegionPath = Paths.get(regionBasePath, sidePath, fileName).toString();
    var scenarioSpecificRegionPath = resourceNames.getScenarioSpecific(defaultRegionPath);

    return Optional.ofNullable(resourceProvider.getResourceInputStream(scenarioSpecificRegionPath))
        .orElseGet(() -> resourceProvider.getResourceInputStream(defaultRegionPath));
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
}
