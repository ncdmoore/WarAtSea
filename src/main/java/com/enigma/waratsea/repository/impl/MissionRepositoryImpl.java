package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.gson.RuntimeTypeAdapterFactory;
import com.enigma.waratsea.entity.mission.BombardmentEntity;
import com.enigma.waratsea.entity.mission.EscortEntity;
import com.enigma.waratsea.entity.mission.FerryShipsEntity;
import com.enigma.waratsea.entity.mission.InterceptEntity;
import com.enigma.waratsea.entity.mission.InvasionEntity;
import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.entity.mission.PatrolEntity;
import com.enigma.waratsea.entity.mission.TransportEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.MissionRepository;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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
public class MissionRepositoryImpl implements MissionRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Inject
  public MissionRepositoryImpl(final GamePaths gamePaths,
                               final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.gamePaths = gamePaths;
  }

  @Override
  public List<MissionEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readMissions(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<MissionEntity> missions) {
    var filePath = getFilePath(side);

    writeMissions(gameId, filePath, missions);
  }

  private List<MissionEntity> readMissions(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read missions: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read missions: '{}'", filePath);
      return Collections.emptyList();
    }
  }

  private void writeMissions(final String gameId, final FilePath filePath, final Set<MissionEntity> missions) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save missions to path: '{}'", path);
      var json = toJson(missions);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save missions to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private List<MissionEntity> toEntities(final BufferedReader bufferedReader) {
    var type = new TypeToken<ArrayList<MissionEntity>>() {
    }.getType();

    RuntimeTypeAdapterFactory<MissionEntity> adapter = RuntimeTypeAdapterFactory
        .of(MissionEntity.class, "type")
        .registerSubtype(BombardmentEntity.class)
        .registerSubtype(EscortEntity.class)
        .registerSubtype(FerryShipsEntity.class)
        .registerSubtype(InterceptEntity.class)
        .registerSubtype(InvasionEntity.class)
        .registerSubtype(PatrolEntity.class)
        .registerSubtype(TransportEntity.class);

    var gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();

    List<MissionEntity> missions = gson.fromJson(bufferedReader, type);

    log.info("load missions: '{}", missions.stream()
        .map(MissionEntity::getId)
        .collect(Collectors.joining(",")));

    return missions;
  }

  private String toJson(final Set<MissionEntity> missions) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(missions);
  }

  private FilePath getFilePath(final Side side) {
    var missionDirectory = gamePaths.getMissionDirectory();
    var missionFileName = gamePaths.getMissionFileName();

    return FilePath.builder()
        .baseDirectory(missionDirectory)
        .side(side)
        .fileName(missionFileName)
        .build();
  }
}
