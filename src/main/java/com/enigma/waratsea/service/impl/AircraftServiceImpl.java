package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.AircraftMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.repository.AircraftRepository;
import com.enigma.waratsea.service.AircraftService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class AircraftServiceImpl implements AircraftService {
  private final AircraftRepository aircraftRepository;

  private final Map<Id, Aircraft> aircraft = new HashMap<>();

  @Inject
  public AircraftServiceImpl(final Events events,
                             final AircraftRepository aircraftRepository) {
    this.aircraftRepository = aircraftRepository;

    registerEvents(events);
  }

  @Override
  public Aircraft get(final Id aircraftId) {
    return aircraft.computeIfAbsent(aircraftId, this::getFromRepository);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
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

  private Aircraft getFromRepository(final Id aircraftId) {
    var entity = aircraftRepository.get(aircraftId);
    return AircraftMapper.INSTANCE.toModel(entity);
  }

  private void clearCache() {
    aircraft.clear();
  }
}
