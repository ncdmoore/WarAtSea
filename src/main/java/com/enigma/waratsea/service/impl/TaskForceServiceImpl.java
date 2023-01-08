package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.mapper.TaskForceMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.repository.TaskForceRepository;
import com.enigma.waratsea.service.TaskForceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Singleton
public class TaskForceServiceImpl implements TaskForceService {
  private final TaskForceRepository taskForceRepository;
  private final TaskForceMapper taskForceMapper;

  private final Map<Side, Set<TaskForce>> taskForceSideMap = new HashMap<>();

  @Inject
  public TaskForceServiceImpl(final Events events,
                              final TaskForceRepository taskForceRepository,
                              final TaskForceMapper taskForceMapper) {
    this.taskForceRepository = taskForceRepository;
    this.taskForceMapper = taskForceMapper;

    registerEvents(events);
  }

  @Override
  public Set<TaskForce> get(Side side) {
   return taskForceSideMap.computeIfAbsent(side, this::getFromRepository);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getLoadTaskForcesEvent().register(this::handleLoadTaskForcesEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    clearCache();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    clearCache();
  }

  private void handleLoadTaskForcesEvent(final LoadTaskForcesEvent loadTaskForcesEvent) {
    log.info("TaskForceServiceImpl handle LoadTaskForcesEvent");

    Side.stream()
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    taskForceSideMap.values()
        .stream()
        .map(taskForceMapper::modelsToEntities)
        .forEach(taskforces -> taskForceRepository.save(gameId, taskforces));
  }

  private Set<TaskForce> getFromRepository(final Side side) {
    var entities = taskForceRepository.get(side);
    var models = taskForceMapper.entitiesToModels(entities);

    return new HashSet<>(models);
  }

  private void clearCache() {
    taskForceSideMap.clear();
  }
}
