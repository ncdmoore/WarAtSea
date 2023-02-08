package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.dto.AllotmentModificationDto;
import com.enigma.waratsea.event.*;
import com.enigma.waratsea.mapper.AllotmentModificationMapper;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.option.AllotmentModification;
import com.enigma.waratsea.model.option.SquadronAllotmentModification;
import com.enigma.waratsea.repository.SquadronAllotmentModRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.SquadronAllotmentModService;
import com.enigma.waratsea.service.SquadronAllotmentService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Singleton
public class SquadronAllotmentModServiceImpl implements SquadronAllotmentModService {
  private final SquadronAllotmentModRepository squadronAllotmentModRepository;
  private final AllotmentModificationMapper allotmentModificationMapper;
  private final SquadronAllotmentService squadronAllotmentService;
  private final GameService gameService;

  private final Map<NationId, Set<AllotmentModification>> modifications = new HashMap<>();

  @Inject
  public SquadronAllotmentModServiceImpl(final Events events,
                                         final SquadronAllotmentModRepository squadronAllotmentModRepository,
                                         final AllotmentModificationMapper allotmentModificationMapper,
                                         final SquadronAllotmentService squadronAllotmentService,
                                         final GameService gameService) {
    this.squadronAllotmentModRepository = squadronAllotmentModRepository;
    this.allotmentModificationMapper = allotmentModificationMapper;
    this.squadronAllotmentService = squadronAllotmentService;
    this.gameService = gameService;

    registerEvents(events);
  }

  @Override
  public Set<AllotmentModification> get(final NationId modificationId) {
    return modifications.get(modificationId);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getLoadScenarioOptionsEvent().register(this::handleScenarioSelectedEvent);
    events.getApplyAllotmentModEvent().register(this::handleAllotmentModEvent);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    clearCache();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    clearCache();
  }

  private void handleScenarioSelectedEvent(final LoadScenarioOptionsEvent loadScenarioOptionsEvent) {
    clearCache();

    loadScenarioOptionsEvent.getScenario()
        .getNationsWithAllotmentOptions()
        .forEach(this::getAllotment);
  }

  private void handleAllotmentModEvent(final ApplyAllotmentModEvent applyAllotmentModEvent) {
    applyAllotmentModEvent.getModifications()
        .forEach(this::applyAllotmentModifications);
  }

  private void applyAllotmentModifications(final NationId nationId, final int id) {
    var modifications = getModifications(nationId, id);
    applyModification(modifications);
  }

  private List<SquadronAllotmentModification> getModifications(final NationId nationId, final int id) {
    return modifications.get(nationId)
        .stream()
        .filter(mod -> id == mod.getId())
        .findAny()
        .map(AllotmentModification::getModifications)
        .orElse(Collections.emptyList());
  }

  private void applyModification(final List<SquadronAllotmentModification> modifications) {
    modifications.stream()
        .map(this::buildModificationDto)
        .forEach(squadronAllotmentService::update);
  }

  private AllotmentModificationDto buildModificationDto(final SquadronAllotmentModification modification) {
    var scenario = gameService.getGame().getScenario();

    return AllotmentModificationDto.builder()
        .allotmentId(modification.getAllotmentId())
        .scenario(scenario)
        .type(modification.getType())
        .dice(modification.getDice())
        .factor(modification.getFactor())
        .build();
  }

  private void getAllotment(final NationId modificationId) {
    log.debug("Get allotment modification for '{}'", modificationId);

    modifications.computeIfAbsent(modificationId, this::getFromRepository);
  }

  private Set<AllotmentModification> getFromRepository(final NationId modificationId) {
    var entities = squadronAllotmentModRepository.get(modificationId);

    return new HashSet<>(allotmentModificationMapper.toModels(entities));
  }

  private void clearCache() {
    modifications.clear();
  }
}
