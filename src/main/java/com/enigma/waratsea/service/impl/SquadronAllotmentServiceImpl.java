package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.event.*;
import com.enigma.waratsea.mapper.AllotmentMapper;
import com.enigma.waratsea.mapper.SquadronMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.Allotment;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.repository.SquadronAllotmentRepository;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.RegionService;
import com.enigma.waratsea.service.SquadronAllotmentService;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Singleton
public class SquadronAllotmentServiceImpl implements SquadronAllotmentService {
  private final SquadronAllotmentRepository squadronAllotmentRepository;
  private final AllotmentMapper allotmentMapper;
  private final SquadronMapper squadronMapper;
  private final RegionService regionService;
  private final DiceService diceService;
  private final SquadronService squadronService;

  private final Map<Id, Allotment> allotments = new HashMap<>();

  private final Map<String, Integer> counts = new HashMap<>();

  @Inject
  public SquadronAllotmentServiceImpl(final Events events,
                                      final SquadronAllotmentRepository squadronAllotmentRepository,
                                      final AllotmentMapper allotmentMapper,
                                      final SquadronMapper squadronMapper,
                                      final RegionService regionService,
                                      final DiceService diceService,
                                      final SquadronService squadronService) {
    this.squadronAllotmentRepository = squadronAllotmentRepository;
    this.allotmentMapper = allotmentMapper;
    this.squadronMapper = squadronMapper;
    this.regionService = regionService;
    this.diceService = diceService;
    this.squadronService = squadronService;

    registerEvents(events);
  }

  public Optional<Allotment> get(final Scenario scenario, final Id allotmentId) {
    var allotment = allotments.computeIfAbsent(allotmentId, id -> getFromRepository(scenario, id));

    return Optional.ofNullable(allotment);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getAllotSquadronEvent().register(this::handleAllotSquadronEvent);
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

  private void handleAllotSquadronEvent(final AllotSquadronEvent allotSquadronEvent) {
    var scenario = allotSquadronEvent.getScenario();

    log.info("SquadronAllotmentServiceImpl handle AllotSquadronEvent for scenario: {} and time frame: {}",
        scenario, scenario.getTimeFrame());

    counts.clear();

    Side.stream().forEach(side -> doAllotmentForSide(side, scenario));
  }

  private void doAllotmentForSide(final Side side, final Scenario scenario) {
    var nations = regionService.getNations(side);
    nations.forEach(nation -> doAllotmentForNation(side, nation, scenario));
  }

  private void doAllotmentForNation(final Side side, final Nation nation, final Scenario scenario) {
    log.info("Perform allotment for side: {}, nation: {}", side, nation);

    var allotmentId = new Id(side, nation.toLower());

    get(scenario, allotmentId)
        .ifPresent(allotment -> createSquadrons(side, allotment));
  }

  private Allotment getFromRepository(final Scenario scenario, final Id allotmentId) {
    var timeFrame = scenario.getTimeFrame();

    return squadronAllotmentRepository.get(timeFrame, allotmentId)
        .map(allotmentMapper::toModel)
        .orElse(null);
  }

  private void createSquadrons(final Side side, final Allotment allotment) {
    var die = diceService.get();

    var squadrons = allotment.get(die)
        .stream()
        .map(this::buildSquadron)
        .collect(toSet());

    squadronService.add(side, squadrons);
  }

  private Squadron buildSquadron(final Id aircraftId) {
    var entity = SquadronEntity.builder()
        .id(buildSquadronId(aircraftId))
        .aircraft(aircraftId)
        .build();

    return squadronMapper.toModel(entity);
  }

  private Id buildSquadronId(final Id aircraftId) {
    var side = aircraftId.getSide();
    var aircraftName = aircraftId.getName();

    var index = counts.computeIfAbsent(aircraftName, name -> 0);
    counts.put(aircraftName, ++index);

    var squadronName = aircraftName + "-A" + index;

    return new Id(side, squadronName);
  }

  private void clearCache() {
    allotments.clear();
  }
}
