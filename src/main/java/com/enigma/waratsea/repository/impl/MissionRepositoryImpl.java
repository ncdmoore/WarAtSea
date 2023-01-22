package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.gson.RuntimeTypeAdapterFactory;
import com.enigma.waratsea.entity.mission.*;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.MissionRepository;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class MissionRepositoryImpl implements MissionRepository {
  private final DataProvider dataProvider;
  private final String missionDirectory;
  private final String missionFileName;

  @Inject
  public MissionRepositoryImpl(final GamePaths gamePaths,
                               final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.missionDirectory = gamePaths.getMissionDirectory();
    this.missionFileName = gamePaths.getMissionFileName();
  }

  @Override
  public List<MissionEntity> get(Side side) {
    return readMissions(new Id(side, missionFileName));
  }

  private List<MissionEntity> readMissions(final Id missionId) {
    try (var in = getInputStream(missionId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read missions: '{}'", missionId);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read missions: '{}'", missionId);
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final Id missionId) {
    return dataProvider.getDataInputStream(missionId, missionDirectory);
  }

  private List<MissionEntity> toEntities(final BufferedReader bufferedReader) {
    var type = new TypeToken<ArrayList<MissionEntity>>() {
    }.getType();

    RuntimeTypeAdapterFactory<MissionEntity> adapter = RuntimeTypeAdapterFactory
        .of(MissionEntity.class, "type")
        .registerSubtype(BombardmentEntity.class)
        .registerSubtype(FerryShipsEntity.class)
        .registerSubtype(InterceptEntity.class)
        .registerSubtype(PatrolEntity.class);

    var gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();

    List<MissionEntity> missions = gson.fromJson(bufferedReader, type);

    log.info("load missions: '{}", missions.stream()
        .map(MissionEntity::getId)
        .collect(Collectors.joining(",")));

    return missions;
  }
}
