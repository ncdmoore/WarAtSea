package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.SquadronAllotmentRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Slf4j
@Singleton
public class SquadronAllotmentRepositoryImpl implements SquadronAllotmentRepository {
  private final ResourceProvider resourceProvider;
  private final String squadronAllotmentDirectory;

  @Inject
  public SquadronAllotmentRepositoryImpl(final GamePaths gamePaths,
                                         final ResourceProvider resourceProvider) {
    this.resourceProvider = resourceProvider;
    this.squadronAllotmentDirectory = gamePaths.getSquadronDeploymentDirectory();
  }

  @Override
  public AllotmentEntity get(final String timeFrame, final Id allotmentId) {
    return readAllotment(timeFrame, allotmentId);
  }

  private AllotmentEntity readAllotment(final String timeFrame, final Id allotmentId) {
    log.debug("Read squadron allotment: '{}'", allotmentId);

    try (var in = getInputStream(timeFrame, allotmentId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return toEntities(br);
    } catch (Exception e) {
      throw new GameException("Unable to squadron load allotment: " + allotmentId);
    }
  }

  private InputStream getInputStream(final String timeFrame, final Id AllotmentId) {
    var baseDirectory = Paths.get(squadronAllotmentDirectory, timeFrame).toString();

    return resourceProvider.getResourceInputStream(AllotmentId, baseDirectory);
  }

  private AllotmentEntity toEntities(final BufferedReader bufferedReader) {
    var gson = new Gson();
    return gson.fromJson(bufferedReader, AllotmentEntity.class);
  }

}
