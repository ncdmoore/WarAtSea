package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.mapper.TaskForceMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.repository.TaskForceRepository;
import com.enigma.waratsea.service.TaskForceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class TaskForceServiceImpl implements TaskForceService {
  private final TaskForceRepository taskForceRepository;
  private final TaskForceMapper taskForceMapper;

  private final Map<Side, Set<TaskForce>> taskForceSideMap = new HashMap<>();
  private final Map<Id, TaskForce> taskForceMap = new HashMap<>();

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

  @Override
  public Set<TaskForce> get(Set<Id> taskForceIds) {
    return taskForceIds.stream()
        .map(taskForceMap::get)
        .collect(Collectors.toSet());
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getLoadTaskForcesEvent().register(this::handleLoadTaskForcesEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    clearCache();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    clearCache();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    clearCache();
  }

  private void handleLoadTaskForcesEvent(final LoadTaskForcesEvent loadTaskForcesEvent) {
    log.info("TaskForceServiceImpl handle LoadTaskForcesEvent");

    Side.stream()
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    taskForceSideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }

  private Set<TaskForce> getFromRepository(final Side side) {
    var entities = taskForceRepository.get(side);
    var models = taskForceMapper.entitiesToModels(entities);

    models.forEach(this::addToTaskForceMap);

    return new HashSet<>(models);
  }

  private void saveSide(final String gameId, final Side side) {
    var taskForces = taskForceSideMap.get(side);
    var entities = taskForceMapper.modelsToEntities(taskForces);
    taskForceRepository.save(gameId, side, entities);
  }

  private void addToTaskForceMap(final TaskForce taskForce) {
    var id = taskForce.getId();
    taskForceMap.putIfAbsent(id, taskForce);
  }

  private void clearCache() {
    taskForceSideMap.clear();
    taskForceMap.clear();
  }
}
