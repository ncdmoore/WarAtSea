package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.event.StartSavedGameEvent;
import com.enigma.waratsea.mapper.AirfieldMapper;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.service.AirfieldService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class AirfieldServiceImpl implements AirfieldService {
  private final AirfieldRepository airfieldRepository;

  private final Map<Id, Airfield> airfields = new HashMap<>();

  @Inject
  public AirfieldServiceImpl(final Events events,
                             final AirfieldRepository airfieldRepository) {
    this.airfieldRepository = airfieldRepository;

    registerEvents(events);
  }

  @Override
  public List<Airfield> get(List<Id> airfieldIds) {
    return airfieldIds.stream()
        .map(this::get)
        .toList();
  }

  @Override
  public Airfield get(Id airfieldId) {
    return airfields.computeIfAbsent(airfieldId, this::getFromDisk);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvents().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvents().register(this::handleStartSavedGameEvent);
  }

  private Airfield getFromDisk(Id airfieldId) {
    var entity = airfieldRepository.get(airfieldId);

    return AirfieldMapper.INSTANCE.toModel(entity);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("AirfieldServiceImpl handle StartNewGameEvent - clear cache");
    airfields.clear();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("AirfieldServiceImpl handle StartSavedGameEvent - clear cache");
    airfields.clear();
  }
}
