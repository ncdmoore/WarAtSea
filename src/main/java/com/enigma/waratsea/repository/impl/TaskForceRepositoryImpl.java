package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.TaskForceEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.TaskForceRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class TaskForceRepositoryImpl implements TaskForceRepository {
  private final DataProvider dataProvider;
  private final String taskForceDirectory;
  private final String taskForceFileName;

  @Inject
  public TaskForceRepositoryImpl(final GamePaths gamePaths,
                                 final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.taskForceDirectory = gamePaths.getTaskForceDirectory();
    this.taskForceFileName = gamePaths.getTaskForceFileName();
  }

  @Override
  public List<TaskForceEntity> get(Side side) {
    return readTaskForces(new Id(side, taskForceFileName));
  }

  @Override
  public void save(final String gameId, final Side side, final Set<TaskForceEntity> taskForces) {
    var id = new Id(side, taskForceFileName);
    var directory = dataProvider.getSavedEntityDirectory(gameId, id, taskForceDirectory);
    writeTaskForces(directory, id, taskForces);
  }

  private List<TaskForceEntity> readTaskForces(final Id taskForceId) {
    try (var in = getInputStream(taskForceId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read Task forces: '{}'", taskForceId);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read task forces: '{}'", taskForceId);
      return Collections.emptyList();
    }
  }

  private void writeTaskForces(final Path directory, final Id id, final Set<TaskForceEntity> taskForces) {
    var filePath = dataProvider.getSaveFile(directory, id);

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save task forces: '{}' to path: '{}'", id, directory);
      var json = toJson(taskForces);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save " + id + " to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final Id taskForceId) {
    return dataProvider.getDataInputStream(taskForceId, taskForceDirectory);
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
}
