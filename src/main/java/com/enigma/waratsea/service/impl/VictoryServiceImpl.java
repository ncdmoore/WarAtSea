package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.*;
import com.enigma.waratsea.event.ship.ShipCargoEvent;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.VictoryMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.victory.Victory;
import com.enigma.waratsea.repository.VictoryRepository;
import com.enigma.waratsea.service.VictoryService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Singleton
public class VictoryServiceImpl implements VictoryService {
  private final VictoryRepository victoryRepository;
  private final VictoryMapper victoryMapper;

  private final Map<Side, Set<Victory>> victorySideMap = new HashMap<>();

  @Inject
  public VictoryServiceImpl(final Events events,
                            final VictoryRepository victoryRepository,
                            final VictoryMapper victoryMapper) {
    this.victoryRepository = victoryRepository;
    this.victoryMapper = victoryMapper;

    registerEvents(events);
  }

  public Set<Victory> get(final Side side) {
    return victorySideMap.computeIfAbsent(side, this::getFromRepository);
  }

  private void registerEvents(final Events events) {
    registerUserEvents(events);
    registerVictoryEvents(events);
  }

  private void registerUserEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getLoadTaskForcesEvent().register(this::handleLoadAssetsEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private void registerVictoryEvents(final Events events) {
    events.getShipCombatEvent().register(this::handleShipCombatEvent);
    events.getShipCargoEvent().register(this::handleShipCargoEvent);
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

    Side.stream()
        .forEach(this::get);
  }

  private void handleShipCargoEvent(final ShipCargoEvent shipCargoEvent) {
    var dto = VictoryDto.builder()
        .shipCargoEvent(shipCargoEvent)
        .build();

    updateVictory(dto);
  }

  private void handleShipCombatEvent(final ShipCombatEvent shipCombatEvent) {
    var dto = VictoryDto.builder()
        .shipCombatEvent(shipCombatEvent)
        .build();

    updateVictory(dto);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    victorySideMap.keySet()
        .forEach(side -> saveSide(gameId, side));
  }


  private Set<Victory> getFromRepository(final Side side) {
    var entities = victoryRepository.get(side);
    var models = victoryMapper.entitiesToModels(entities);

    return new HashSet<>(models);
  }


  private void saveSide(final String gameId, final Side side) {
    var victories = victorySideMap.get(side);
    var entities = victoryMapper.modelsToEntities(victories);
    victoryRepository.save(gameId, side, entities);
  }

  private void updateVictory(final VictoryDto dto) {
    victorySideMap.values()
        .stream()
        .flatMap(Collection::stream)
        .forEach(victory -> victory.handleEvent(dto));
  }

  private void clearCache() {
    victorySideMap.clear();
  }
}
