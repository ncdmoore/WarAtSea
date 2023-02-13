package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.AirfieldMapper;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.service.AirfieldService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Singleton
public class AirfieldServiceImpl implements AirfieldService {
  private final AirfieldRepository airfieldRepository;
  private final AirfieldMapper airfieldMapper;

  private final Map<Id, Airfield> airfields = new HashMap<>();
  private final Map<Side, Set<Airfield>> airfieldSideMap = new HashMap<>();

  @Inject
  public AirfieldServiceImpl(final Events events,
                             final AirfieldRepository airfieldRepository,
                             final AirfieldMapper airfieldMapper) {
    this.airfieldRepository = airfieldRepository;
    this.airfieldMapper = airfieldMapper;

    registerEvents(events);
  }

  @Override
  public List<Airfield> get(final List<Id> airfieldIds) {
    return airfieldIds.stream()
        .map(this::get)
        .toList();
  }

  @Override
  public Set<Airfield> get(final Side side) {
    return Optional.ofNullable(airfieldSideMap.get(side))
        .orElse(Collections.emptySet());
  }

  @Override
  public Airfield get(final Id airfieldId) {
    return  airfields.computeIfAbsent(airfieldId, this::getAndIndex);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private Airfield getAndIndex(final Id airfieldId) {
    var airfield = getFromRepository(airfieldId);

    var side = airfieldId.getSide();

    airfieldSideMap.computeIfAbsent(side, s -> new HashSet<>())
        .add(airfield);

    return airfield;
  }

  private Airfield getFromRepository(final Id airfieldId) {
    var entity = airfieldRepository.get(airfieldId);

    return airfieldMapper.toModel(entity);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("AirfieldServiceImpl handle StartNewGameEvent - clear cache");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("AirfieldServiceImpl handle StartSavedGameEvent - clear cache");
    clearCaches();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    log.debug("AirfieldServiceImpl handle SelectedScenarioEvent - clear cache");
    clearCaches();
  }

  private void handleClearEvent(final ClearEvent clearEvent) {
    log.debug("AirfieldServiceImpl handle ClearEvent - clear cache");
    clearCaches();
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    airfields.values()
        .stream()
        .map(AirfieldMapper.INSTANCE::toEntity)
        .forEach(airfield -> airfieldRepository.save(gameId, airfield));

    log.info("AirfieldServiceImpl received saveGameEvent save airfields for game: '{}'.", gameId);
  }

  private void clearCaches() {
    airfields.clear();
    airfieldSideMap.clear();
  }
}
