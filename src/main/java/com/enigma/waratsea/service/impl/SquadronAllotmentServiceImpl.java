package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.event.AllotSquadronEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.mapper.AllotmentMapper;
import com.enigma.waratsea.mapper.SquadronMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
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

import static com.enigma.waratsea.model.squadron.SquadronConfiguration.NONE;
import static com.enigma.waratsea.model.squadron.SquadronState.CREATED;
import static com.enigma.waratsea.model.squadron.SquadronStrength.FULL;
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

  private void registerEvents(final Events events) {
    events.getAllotSquadronEvent().register(this::handleAllotSquadronEvent);
  }

  private void handleAllotSquadronEvent(final AllotSquadronEvent allotSquadronEvent) {
    var scenario = allotSquadronEvent.getScenario();
    var timeFrame = scenario.getTimeFrame();

    log.info("SquadronAllotmentServiceImpl handle AllotSquadronEvent for scenario: {} and time frame: {}",
        scenario, timeFrame);

    counts.clear();

    Side.stream().forEach(side -> doAllotmentForSide(side, timeFrame));
  }

  private void doAllotmentForSide(final Side side, final String timeFrame) {
    var nations = regionService.getNations(side);
    nations.forEach(nation -> doAllotmentForNation(side, nation, timeFrame));
  }

  private void doAllotmentForNation(final Side side, final Nation nation, final String timeFrame) {
    log.info("Perform allotment for side: {}, nation: {}", side, nation);

    var allotmentId = new Id(side, nation.toLower());
    squadronAllotmentRepository.get(timeFrame, allotmentId)
        .ifPresent(allotment -> createSquadrons(side, allotment));
  }

  private void createSquadrons(final Side side, final AllotmentEntity allotmentEntity) {
    var allotment = allotmentMapper.toModel(allotmentEntity);
    var die = diceService.get(6);

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
        .state(CREATED)
        .configuration(NONE)
        .strength(FULL)
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
}
