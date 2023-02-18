package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadTaskForcesEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.SubmarineFlotillaMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.SubmarineFlotilla;
import com.enigma.waratsea.repository.SubmarineFlotillaRepository;
import com.enigma.waratsea.service.SubmarineFlotillaService;
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
public class SubmarineFlotillaServiceImpl implements SubmarineFlotillaService {
  private final SubmarineFlotillaRepository submarineFlotillaRepository;
  private final SubmarineFlotillaMapper submarineFlotillaMapper;

  private final Map<Side, Set<SubmarineFlotilla>> flotillaSideMap = new HashMap<>();
  private final Map<Id, SubmarineFlotilla> flotillaMap = new HashMap<>();


  @Inject
  public SubmarineFlotillaServiceImpl(final Events events,
                                      final SubmarineFlotillaRepository submarineFlotillaRepository,
                                      final SubmarineFlotillaMapper submarineFlotillaMapper) {
    this.submarineFlotillaRepository = submarineFlotillaRepository;
    this.submarineFlotillaMapper = submarineFlotillaMapper;

    registerEvents(events);
  }

  @Override
  public Set<SubmarineFlotilla> get(final Side side) {
    return flotillaSideMap.computeIfAbsent(side, this::getFromRepository);
  }

  @Override
  public Set<SubmarineFlotilla> get(final Set<Id> flotillaIds) {
    return flotillaIds.stream()
        .map(flotillaMap::get)
        .collect(Collectors.toSet());
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
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

  private void handleClearEvent(final ClearEvent clearEvent) {
    clearCache();
  }

  private void handleLoadTaskForcesEvent(final LoadTaskForcesEvent loadTaskForcesEvent) {
    log.info("SubmarineFlotillaImpl handle LoadTaskForcesEvent");

    Side.stream()
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    flotillaSideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }

  private Set<SubmarineFlotilla> getFromRepository(final Side side) {
    var entities = submarineFlotillaRepository.get(side);
    var models = submarineFlotillaMapper.entitiesToModels(entities);

    models.forEach(this::addToFlotillaMap);

    return new HashSet<>(models);
  }

  private void saveSide(final String gameId, final Side side) {
    var taskForces = flotillaSideMap.get(side);
    var entities = submarineFlotillaMapper.modelsToEntities(taskForces);
    submarineFlotillaRepository.save(gameId, side, entities);
  }

  private void addToFlotillaMap(final SubmarineFlotilla submarineFlotilla) {
    var id = submarineFlotilla.getId();
    flotillaMap.putIfAbsent(id, submarineFlotilla);
  }

  private void clearCache() {
    flotillaSideMap.clear();
    flotillaMap.clear();
  }
}
