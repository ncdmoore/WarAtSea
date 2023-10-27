package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.gson.RuntimeTypeAdapterFactory;
import com.enigma.waratsea.entity.release.ReleaseEntity;
import com.enigma.waratsea.entity.release.ShipCombatReleaseEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.ReleaseRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ReleaseRepositoryImpl implements ReleaseRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<ReleaseEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readReleaseConditions(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<ReleaseEntity> victoryConditions) {
    var filePath = getFilePath(side);

    writeReleaseConditions(gameId, filePath, victoryConditions);
  }

  private List<ReleaseEntity> readReleaseConditions(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read release conditions: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read release conditions: '{}' {} '{}'", filePath, e.getClass(), e.getMessage());
      return Collections.emptyList();
    }
  }

  private void writeReleaseConditions(final String gameId, final FilePath filePath, final Set<ReleaseEntity> victoryConditions) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save release conditions to path: '{}'", path);
      var json = toJson(victoryConditions);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save release conditions to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private List<ReleaseEntity> toEntities(final BufferedReader bufferedReader) {
    var type = new TypeToken<ArrayList<ReleaseEntity>>() {
    }.getType();

    var adapter = RuntimeTypeAdapterFactory
        .of(ReleaseEntity.class, "type")
        .registerSubtype(ShipCombatReleaseEntity.class);

    var gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();

    List<ReleaseEntity> releaseEntities = gson.fromJson(bufferedReader, type);

    log.info("load release conditions: '{}", releaseEntities.stream()
        .map(ReleaseEntity::getId)
        .collect(Collectors.joining(",")));

    return releaseEntities;
  }

  private String toJson(final Set<ReleaseEntity> releaseEntities) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(releaseEntities);
  }

  private FilePath getFilePath(final Side side) {
    var releaseDirectory = gamePaths.getReleaseDirectory();
    var releaseFileName = gamePaths.getReleaseFileName();

    return FilePath.builder()
        .baseDirectory(releaseDirectory)
        .side(side)
        .fileName(releaseFileName)
        .build();
  }
}
