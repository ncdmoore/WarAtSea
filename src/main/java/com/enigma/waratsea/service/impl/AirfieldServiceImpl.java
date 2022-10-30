package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.SaveGameEvent;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.event.StartSavedGameEvent;
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

  private final Map<Id, Airfield> airfields = new HashMap<>();
  private final Map<Side, Set<Airfield>> airfieldSideMap = new HashMap<>();

  @Inject
  public AirfieldServiceImpl(final Events events,
                             final AirfieldRepository airfieldRepository) {
    this.airfieldRepository = airfieldRepository;

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
    events.getStartNewGameEvents().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvents().register(this::handleStartSavedGameEvent);
    events.getSaveGameEvents().register(this::save);
  }

  private Airfield getAndIndex(Id airfieldId) {
    var airfield = getFromDisk(airfieldId);

    var side = airfieldId.getSide();

    airfieldSideMap.computeIfAbsent(side, s -> new HashSet<>())
        .add(airfield);

    return airfield;
  }

  private Airfield getFromDisk(Id airfieldId) {
    var entity = airfieldRepository.get(airfieldId);

    return AirfieldMapper.INSTANCE.toModel(entity);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("AirfieldServiceImpl handle StartNewGameEvent - clear cache");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("AirfieldServiceImpl handle StartSavedGameEvent - clear cache");
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
