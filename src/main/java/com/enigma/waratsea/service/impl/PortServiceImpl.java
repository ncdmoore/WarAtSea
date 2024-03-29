package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.PortMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.repository.PortRepository;
import com.enigma.waratsea.service.PortService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class PortServiceImpl implements PortService {
  private final PortRepository portRepository;

  private final Map<Id, Port> ports = new HashMap<>();
  private final Map<Side, Set<Port>> portSideMap = new HashMap<>();

  @Inject
  public PortServiceImpl(final Events events,
                            final PortRepository portRepository) {
    this.portRepository = portRepository;

    registerEvents(events);
  }

  @Override
  public Set<Port> get(final Set<Id> portIds) {
    return portIds.stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Port> get(final Side side) {
    return Optional.ofNullable(portSideMap.get(side))
        .orElse(Collections.emptySet());
  }

  @Override
  public Port get(final Id portId) {
    return ports.computeIfAbsent(portId, this::getAndIndex);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private Port getAndIndex(final Id portId) {
    var port = getFromRepository(portId);

    var side = portId.getSide();

    portSideMap.computeIfAbsent(side, s -> new HashSet<>())
        .add(port);

    return port;
  }

  private Port getFromRepository(final Id portId) {
    var entity = portRepository.get(portId);

    return PortMapper.INSTANCE.toModel(entity);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("PortServiceImpl handle StartNewGameEvent - clear cache");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("PortServiceImpl handle StartSavedGameEvent - clear cache");
    clearCaches();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    log.debug("PortServiceImpl handle SelectScenarioEvent - clear cache");
    clearCaches();
  }

  private void handleClearEvent(final ClearEvent clearEvent) {
    log.debug("PortServiceImpl handle ClearEvent - clear cache");
    clearCaches();
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    ports.values()
        .stream()
        .map(PortMapper.INSTANCE::toEntity)
        .forEach(port -> portRepository.save(gameId, port));

    log.info("PortServiceImpl received saveGameEvent save ports for game: '{}'.", gameId);
  }

  private void clearCaches() {
    ports.clear();
    portSideMap.clear();
  }
}
