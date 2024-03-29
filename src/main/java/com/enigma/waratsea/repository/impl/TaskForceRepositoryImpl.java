package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.taskforce.TaskForceEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.TaskForceRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.gson.Gson;
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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class TaskForceRepositoryImpl implements TaskForceRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<TaskForceEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readTaskForces(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<TaskForceEntity> taskForces) {
    var filePath = getFilePath(side);

    writeTaskForces(gameId, filePath, taskForces);
  }

  private List<TaskForceEntity> readTaskForces(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read task forces: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read task forces: '{}' {} '{}'", filePath, e.getClass(), e.getMessage());
      return Collections.emptyList();
    }
  }

  private void writeTaskForces(final String gameId, final FilePath filePath, final Set<TaskForceEntity> taskForces) {
    try (var out = getOutputStream(gameId, filePath);
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save task forces for game: '{}' to path: '{}'", gameId, filePath);
      var json = toJson(taskForces);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save task forces for game: " + gameId + "to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getInputStream(filePath);
  }

  private OutputStream getOutputStream(final String gameId, final FilePath filePath) throws FileNotFoundException {
    return dataProvider.getOutputStream(gameId, filePath);
  }

  private List<TaskForceEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<TaskForceEntity>>() {
    }.getType();

    var gson = new Gson();
    List<TaskForceEntity> taskForces = gson.fromJson(bufferedReader, collectionType);

    log.debug("load task forces: '{}',", taskForces.stream()
        .map(TaskForceEntity::getId)
        .map(Id::toString)
        .collect(Collectors.joining(",")));

    return taskForces;
  }

  private String toJson(final Set<TaskForceEntity> taskForces) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(taskForces);
  }

  private FilePath getFilePath(final Side side) {
    var taskForceDirectory = gamePaths.getTaskForceDirectory();
    var taskForceFileName = gamePaths.getTaskForceFileName();

    return FilePath.builder()
        .baseDirectory(taskForceDirectory)
        .side(side)
        .fileName(taskForceFileName)
        .build();
  }
}
