package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.MtbFlotillaMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.repository.MtbFlotillaRepository;
import com.enigma.waratsea.service.MtbFlotillaService;
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
public class MtbFlotillaServiceImpl implements MtbFlotillaService {
  private final MtbFlotillaRepository MtbFlotillaRepository;
  private final MtbFlotillaMapper MtbFlotillaMapper;

  private final Map<Side, Set<MtbFlotilla>> flotillaSideMap = new HashMap<>();
  private final Map<Id, MtbFlotilla> flotillaMap = new HashMap<>();


  @Inject
  public MtbFlotillaServiceImpl(final Events events,
                                final MtbFlotillaRepository MtbFlotillaRepository,
                                final MtbFlotillaMapper MtbFlotillaMapper) {
    this.MtbFlotillaRepository = MtbFlotillaRepository;
    this.MtbFlotillaMapper = MtbFlotillaMapper;

    registerEvents(events);
  }

  @Override
  public Set<MtbFlotilla> get(Side side) {
    return flotillaSideMap.computeIfAbsent(side, this::getFromRepository);
  }

  @Override
  public Set<MtbFlotilla> get(Set<Id> flotillaIds) {
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
    log.info("MtbFlotillaImpl handle LoadTaskForcesEvent");

    Side.stream()
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    flotillaSideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }

  private Set<MtbFlotilla> getFromRepository(final Side side) {
    var entities = MtbFlotillaRepository.get(side);
    var models = MtbFlotillaMapper.entitiesToModels(entities);

    models.forEach(this::addToFlotillaMap);

    return new HashSet<>(models);
  }

  private void saveSide(final String gameId, final Side side) {
    var taskForces = flotillaSideMap.get(side);
    var entities = MtbFlotillaMapper.modelsToEntities(taskForces);
    MtbFlotillaRepository.save(gameId, side, entities);
  }

  private void addToFlotillaMap(final MtbFlotilla MtbFlotilla) {
    var id = MtbFlotilla.getId();
    flotillaMap.putIfAbsent(id, MtbFlotilla);
  }

  private void clearCache() {
    flotillaSideMap.clear();
    flotillaMap.clear();
  }
}
