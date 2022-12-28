package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.event.StartSavedGameEvent;
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
  public Set<Squadron> get(Side side) {
    return Optional.ofNullable(squadronSideMap.get(side))
        .orElse(Collections.emptySet());
  }

  @Override
  public Squadron get(Id squadronId) {
    return squadrons.computeIfAbsent(squadronId, this::getAndIndex);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("SquadronServiceImpl handle StartNewGameEvent - clear cache");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("SquadronServiceImpl handle StartSavedGameEvent - clear cache");
    clearCaches();
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

  private void clearCaches() {
    squadrons.clear();
    squadronSideMap.clear();
  }
}
