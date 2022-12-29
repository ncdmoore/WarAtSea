package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.SquadronDeploymentEntity;
import com.enigma.waratsea.model.Id;
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
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SquadronDeploymentRepositoryImpl implements SquadronDeploymentRepository {
  private final DataProvider dataProvider;
  private final String squadronDeploymentDirectory;
  private final String squadronDeploymentName;

  @Inject
  public SquadronDeploymentRepositoryImpl(final GamePaths gamePaths,
                                          final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.squadronDeploymentDirectory = gamePaths.getSquadronDeploymentDirectory();
    this.squadronDeploymentName = gamePaths.getSquadronDeploymentName();
  }

  @Override
  public List<SquadronDeploymentEntity> get(Side side) {
    return readDeployment(new Id(side, squadronDeploymentName));
  }

  private List<SquadronDeploymentEntity> readDeployment(final Id deploymentId) {
    try (var in = getInputStream(deploymentId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read squadron deployment: '{}'", deploymentId);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read deployment: '{}'", deploymentId);
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final Id deploymentId) {
    return dataProvider.getDataInputStream(deploymentId, squadronDeploymentDirectory);
  }

  private List<SquadronDeploymentEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<SquadronDeploymentEntity>>() {
    }.getType();

    var gson = new Gson();
    List<SquadronDeploymentEntity> deployment = gson.fromJson(bufferedReader, collectionType);

    log.debug("load deployment: '{}',", deployment.stream()
        .map(SquadronDeploymentEntity::getAirbases)
        .map(this::getAirbaseIdsAsString)
        .collect(Collectors.joining(",")));

    return deployment;
  }

  private String getAirbaseIdsAsString(final List<Id> airbaseIds) {
    return airbaseIds.stream().map(Id::toString).collect(Collectors.joining(","));
  }
}