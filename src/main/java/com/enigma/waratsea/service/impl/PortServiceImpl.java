package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.SaveGameEvent;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.event.StartSavedGameEvent;
import com.enigma.waratsea.mapper.PortMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Port;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.PortRepository;
import com.enigma.waratsea.service.PortService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
  public List<Port> get(List<Id> portIds) {
    return portIds.stream()
        .map(this::get)
        .toList();
  }

  @Override
  public Set<Port> get(Side side) {
    return Optional.ofNullable(portSideMap.get(side))
        .orElse(Collections.emptySet());
  }

  @Override
  public Port get(Id portId) {
    return ports.computeIfAbsent(portId, this::getAndIndex);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private Port getAndIndex(Id portId) {
    var port = getFromRepository(portId);

    var side = portId.getSide();

    portSideMap.computeIfAbsent(side, s -> new HashSet<>())
        .add(port);

    return port;
  }

  private Port getFromRepository(Id portId) {
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
