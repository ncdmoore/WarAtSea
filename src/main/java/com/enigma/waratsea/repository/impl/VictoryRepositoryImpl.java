package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.gson.RuntimeTypeAdapterFactory;
import com.enigma.waratsea.entity.victory.ShipBombardmentVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipCargoLostVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipCargoUnloadedVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipDamagedVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipOutOfFuelVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipSunkVictoryEntity;
import com.enigma.waratsea.entity.victory.SquadronStepDestroyedVictoryEntity;
import com.enigma.waratsea.entity.victory.VictoryEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.VictoryRepository;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
public class VictoryRepositoryImpl implements VictoryRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<VictoryEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readVictoryConditions(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<VictoryEntity> victoryConditions) {
    var filePath = getFilePath(side);

    writeVictoryConditions(gameId, filePath, victoryConditions);
  }

  private List<VictoryEntity> readVictoryConditions(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read victory conditions: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read victory conditions: '{}'", filePath, e);
      return Collections.emptyList();
    }
  }

  private void writeVictoryConditions(final String gameId, final FilePath filePath, final Set<VictoryEntity> victoryConditions) {
    try (var out = getOutputStream(gameId, filePath);
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save victory conditions for game: '{}' to path: '{}'", gameId, filePath);
      var json = toJson(victoryConditions);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save victory conditions for game: " + gameId + "to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getInputStream(filePath);
  }

  private OutputStream getOutputStream(final String gameId, final FilePath filePath) throws FileNotFoundException {
    return dataProvider.getOutputStream(gameId, filePath);
  }

  private List<VictoryEntity> toEntities(final BufferedReader bufferedReader) {
    var type = new TypeToken<ArrayList<VictoryEntity>>() {
    }.getType();

    var adapter = RuntimeTypeAdapterFactory
        .of(VictoryEntity.class, "type")
        .registerSubtype(ShipBombardmentVictoryEntity.class)
        .registerSubtype(ShipCargoLostVictoryEntity.class)
        .registerSubtype(ShipCargoUnloadedVictoryEntity.class)
        .registerSubtype(ShipDamagedVictoryEntity.class)
        .registerSubtype(ShipOutOfFuelVictoryEntity.class)
        .registerSubtype(ShipSunkVictoryEntity.class)
        .registerSubtype(SquadronStepDestroyedVictoryEntity.class);

    var gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();

    List<VictoryEntity> victoryConditions = gson.fromJson(bufferedReader, type);

    log.info("load victory conditions: '{}", victoryConditions.stream()
        .map(VictoryEntity::getId)
        .collect(Collectors.joining(",")));

    return victoryConditions;
  }

  private String toJson(final Set<VictoryEntity> victoryConditions) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(victoryConditions);
  }

  private FilePath getFilePath(final Side side) {
    var victoryDirectory = gamePaths.getVictoryDirectory();
    var victoryFileName = gamePaths.getVictoryFileName();

    return FilePath.builder()
        .baseDirectory(victoryDirectory)
        .side(side)
        .fileName(victoryFileName)
        .build();
  }
}
