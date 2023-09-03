package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.option.AllotmentModificationEntity;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.repository.SquadronAllotmentModRepository;
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
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class SquadronAllotmentModRepositoryImpl implements SquadronAllotmentModRepository {
  private final ResourceProvider resourceProvider;
  private final GamePaths gamePaths;

  @Override
  public List<AllotmentModificationEntity> get(final NationId modificationId) {
    var filePath = getFilePath(modificationId);

    return readAllotmentModifications(filePath);
  }

  private List<AllotmentModificationEntity> readAllotmentModifications(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read allotment modification: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read allotment modification: '{}'", filePath, e);
      return Collections.emptyList();
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return resourceProvider.getResourceInputStream(filePath);
  }

  private List<AllotmentModificationEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<AllotmentModificationEntity>>() {
    }.getType();

    var gson = new Gson();
    List<AllotmentModificationEntity> allotmentModifications = gson.fromJson(bufferedReader, collectionType);

    log.debug("load allotment modifications: '{}',", allotmentModifications.stream()
        .map(AllotmentModificationEntity::getId)
        .map(String::valueOf)
        .collect(Collectors.joining(",")));

    return allotmentModifications;
  }

  private FilePath getFilePath(final NationId modificationId) {
    var squadronAllotmentModDirectory = gamePaths.getSquadronAllotmentModDirectory();

    return FilePath.builder()
        .baseDirectory(squadronAllotmentModDirectory)
        .side(modificationId.getSide())
        .fileName(modificationId.getNation().toLower())
        .build();
  }
}
