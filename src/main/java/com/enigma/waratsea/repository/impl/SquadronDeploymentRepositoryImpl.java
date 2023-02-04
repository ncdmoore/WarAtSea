package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.SquadronDeploymentEntity;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
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

@Slf4j
@Singleton
public class SquadronDeploymentRepositoryImpl implements SquadronDeploymentRepository {
  private final DataProvider dataProvider;
  private final String squadronDeploymentDirectory;
  private final String squadronDeploymentFileName;

  @Inject
  public SquadronDeploymentRepositoryImpl(final GamePaths gamePaths,
                                          final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.squadronDeploymentDirectory = gamePaths.getSquadronDeploymentDirectory();
    this.squadronDeploymentFileName = gamePaths.getSquadronDeploymentFileName();
  }

  @Override
  public List<SquadronDeploymentEntity> get(Side side) {
    var filePath = getFilePath(side);

    return readDeployment(filePath);
  }

  private List<SquadronDeploymentEntity> readDeployment(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read squadron deployment: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read deployment: '{}'", filePath);
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private List<SquadronDeploymentEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<SquadronDeploymentEntity>>() {
    }.getType();

    var gson = new Gson();
    return gson.fromJson(bufferedReader, collectionType);
  }

  private FilePath getFilePath(final Side side) {
    return FilePath.builder()
        .baseDirectory(squadronDeploymentDirectory)
        .side(side)
        .fileName(squadronDeploymentFileName)
        .build();
  }
}
