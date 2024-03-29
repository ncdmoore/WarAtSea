package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.SquadronDeploymentEntity;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class SquadronDeploymentRepositoryImpl implements SquadronDeploymentRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<SquadronDeploymentEntity> get(final Side side) {
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
      log.warn("Unable to read deployment: '{}' {} '{}'", filePath, e.getClass(), e.getMessage());
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getInputStream(filePath);
  }

  private List<SquadronDeploymentEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<SquadronDeploymentEntity>>() {
    }.getType();

    var gson = new Gson();
    return gson.fromJson(bufferedReader, collectionType);
  }

  private FilePath getFilePath(final Side side) {
    var squadronDeploymentDirectory = gamePaths.getSquadronDeploymentDirectory();
    var squadronDeploymentFileName = gamePaths.getSquadronDeploymentFileName();

    return FilePath.builder()
        .baseDirectory(squadronDeploymentDirectory)
        .side(side)
        .fileName(squadronDeploymentFileName)
        .build();
  }
}
