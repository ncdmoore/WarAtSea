package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.repository.SquadronAllotmentRepository;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class SquadronAllotmentRepositoryImpl implements SquadronAllotmentRepository {
  private final ResourceProvider resourceProvider;
  private final GamePaths gamePaths;

  @Override
  public Optional<AllotmentEntity> get(final String timeFrame, final NationId allotmentId) {
    var filePath = getFilePath(timeFrame, allotmentId);

    return readAllotment(filePath);
  }

  private Optional<AllotmentEntity> readAllotment(final FilePath filePath) {
    log.info("Read squadron allotment: '{}'", filePath);

    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return Optional.of(toEntities(br));
    } catch (Exception e) {
      log.warn("Unable to squadron load allotment: '{}', {}, '{}'", filePath, e.getClass(), e.getMessage());
      return Optional.empty();
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return resourceProvider.getInputStream(filePath);
  }

  private AllotmentEntity toEntities(final BufferedReader bufferedReader) {
    var gson = new Gson();
    return gson.fromJson(bufferedReader, AllotmentEntity.class);
  }

  private FilePath getFilePath(final String timeFrame, final NationId allotmentId) {
    var squadronAllotmentDirectory = gamePaths.getSquadronAllotmentDirectory();
    var baseDirectory = Paths.get(squadronAllotmentDirectory, timeFrame).toString();

    return FilePath.builder()
        .baseDirectory(baseDirectory)
        .side(allotmentId.getSide())
        .fileName(allotmentId.getNation().toLower())
        .build();
  }
}
