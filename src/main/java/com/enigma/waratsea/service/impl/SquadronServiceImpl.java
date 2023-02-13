package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.SquadronMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.repository.SquadronRepository;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SquadronServiceImpl implements SquadronService {
  private final SquadronRepository squadronRepository;
  private final SquadronMapper squadronMapper;

  private final Map<Id, Squadron> squadrons = new HashMap<>();
  private final Map<Side, Set<Squadron>> squadronSideMap = new HashMap<>();

  @Inject
  public SquadronServiceImpl(final Events events,
                             final SquadronRepository squadronRepository,
                             final SquadronMapper squadronMapper) {
    this.squadronRepository = squadronRepository;
    this.squadronMapper = squadronMapper;

    registerEvents(events);
  }

  @Override
  public Set<Squadron> get(final Set<Id> squadronIds) {
    return squadronIds.stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Squadron> get(final Side side) {
    return Optional.ofNullable(squadronSideMap.get(side))
        .orElse(Collections.emptySet());
  }

  @Override
  public Squadron get(final Id squadronId) {
    return squadrons.computeIfAbsent(squadronId, this::getAndIndex);
  }

  @Override
  public void add(final Side side, final Set<Squadron> newSquadrons) {
    newSquadrons.forEach(squadron -> squadrons.put(squadron.getId(), squadron));

    squadronSideMap.get(side).addAll(newSquadrons);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getLoadSquadronsEvent().register(this::handleLoadSquadronsEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("SquadronServiceImpl handle StartNewGameEvent - clear cache");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("SquadronServiceImpl handle StartSavedGameEvent - clear cache");
    clearCaches();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    log.debug("SquadronServiceImpl handle SelectedScenarioEvent - clear cache");
    clearCaches();
  }

  private void handleClearEvent(final ClearEvent clearEvent) {
    log.debug("SquadronServiceImpl handle ClearEvent - clear cache");
    clearCaches();
  }

  private void handleLoadSquadronsEvent(final LoadSquadronsEvent loadSquadronsEvent) {
    Side.stream()
        .forEach(this::loadSquadronsForSide);
  }

  private void loadSquadronsForSide(final Side side) {
    squadronRepository.getManifest(side)
        .forEach(this::get);
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();
    log.info("SquadronServiceImpl received saveGameEvent save squadrons for game: '{}'.", gameId);

    saveSquadrons(gameId);
    saveManifest(gameId);
  }

  private Squadron getAndIndex(final Id squadronId) {
    var squadron = getFromRepository(squadronId);

    var side = squadronId.getSide();

    squadronSideMap.computeIfAbsent(side, s -> new HashSet<>())
        .add(squadron);

    return squadron;
  }

  private Squadron getFromRepository(final Id squadronId) {
    var entity = squadronRepository.get(squadronId);

    return squadronMapper.toModel(entity);
  }

  private void saveSquadrons(final String gameId) {
    squadrons.values()
        .stream()
        .map(squadronMapper::toEntity)
        .forEach(squadron -> squadronRepository.save(gameId, squadron));
  }

  private void saveManifest(final String gameId) {
    Side.stream()
        .forEach(side -> saveSquadronManifestForSide(gameId, side));
  }

  private void saveSquadronManifestForSide(final String gameId, final Side side) {
    var squadrons = getSquadronIdsForSide(side);
    squadronRepository.saveManifest(gameId, side, squadrons);
  }

  private Set<Id> getSquadronIdsForSide(final Side side) {
    return squadrons.values().stream()
        .map(Squadron::getId)
        .filter(id -> id.getSide() == side)
        .collect(Collectors.toSet());
  }

  private void clearCaches() {
    squadrons.clear();
    squadronSideMap.clear();
  }
}
