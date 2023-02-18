package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadTaskForcesEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.ReleaseMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.release.Release;
import com.enigma.waratsea.repository.ReleaseRepository;
import com.enigma.waratsea.service.ReleaseService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Singleton
public class ReleaseServiceImpl implements ReleaseService {
  private final Events events;
  private final ReleaseRepository releaseRepository;
  private final ReleaseMapper releaseMapper;

  private final Map<Side, Set<Release>> releaseSideMap = new HashMap<>();

  @Inject
  public ReleaseServiceImpl(final Events events,
                            final ReleaseRepository releaseRepository,
                            final ReleaseMapper releaseMapper) {
    this.events = events;
    this.releaseRepository = releaseRepository;
    this.releaseMapper = releaseMapper;

    registerEvents();
  }

  public Set<Release> get(final Side side) {
    return releaseSideMap.computeIfAbsent(side, this::getFromRepository);
  }

  private void registerEvents() {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getLoadTaskForcesEvent().register(this::handleLoadAssetsEvent);
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

  private void handleLoadAssetsEvent(final LoadTaskForcesEvent loadTaskForcesEvent) {
    log.info("Handle LoadTaskForcesEvent");

    Side.combatants()
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    releaseSideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }

  private Set<Release> getFromRepository(final Side side) {
    var entities = releaseRepository.get(side);
    var models = releaseMapper.entitiesToModels(entities);

    models.forEach(release -> {
      release.registerEvents(events);
      addReleaseToTaskForces(release);
    });

    return new HashSet<>(models);
  }

  private void saveSide(final String gameId, final Side side) {
    var victories = releaseSideMap.get(side);
    var entities = releaseMapper.modelsToEntities(victories);
    releaseRepository.save(gameId, side, entities);
  }

  private void addReleaseToTaskForces(final Release release) {
    release.getTaskForces()
        .forEach(taskForce -> taskForce.addRelease(release));
  }

  private void clearCache() {
    releaseSideMap.clear();
  }
}
