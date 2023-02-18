package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMissionsEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.MissionMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.repository.MissionRepository;
import com.enigma.waratsea.service.MissionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Singleton
public class MissionServiceImpl implements MissionService {
  private final MissionRepository missionRepository;
  private final MissionMapper missionMapper;

  private final Map<Side, Set<Mission>> missionSideMap = new HashMap<>();

  @Inject
  public MissionServiceImpl(final Events events,
                            final MissionRepository missionRepository,
                            final MissionMapper missionMapper) {
    this.missionRepository = missionRepository;
    this.missionMapper = missionMapper;

    registerEvents(events);
  }

  @Override
  public Set<Mission> get(final Side side) {
    return missionSideMap.computeIfAbsent(side, this::getFromRepository);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getLoadMissionsEvent().register(this::handleLoadMissionsEvent);
    events.getClearEvent().register(this::handleClearEvent);
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

  private void handleLoadMissionsEvent(final LoadMissionsEvent loadMissionsEvent) {
    log.info("MissionServiceImpl handle LoadMissionsEvent");

    Side.stream().forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    missionSideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }

  private Set<Mission> getFromRepository(final Side side) {
    var entities = missionRepository.get(side);
    var missions = missionMapper.entitiesToModels(entities);

    setTaskForceMissions(missions);

    return new HashSet<>(missions);
  }

  private void saveSide(final String gameId, final Side side) {
    var missions = missionSideMap.get(side);
    var entities = missionMapper.modelsToEntities(missions);
    missionRepository.save(gameId, side, entities);
  }

  private void setTaskForceMissions(final List<Mission> missions) {
    missions.forEach(mission -> mission.getTaskForces()
        .forEach(taskForce -> taskForce.addMission(mission)));
  }

  private void clearCache() {
    missionSideMap.clear();
  }
}
