package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.DeploySquadronEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.mapper.SquadronDeploymentMapper;
import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.SquadronDeployment;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
import com.enigma.waratsea.service.AirbaseService;
import com.enigma.waratsea.service.SquadronDeploymentService;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SquadronDeploymentServiceImpl implements SquadronDeploymentService {
  private final SquadronDeploymentRepository squadronDeploymentRepository;
  private final AirbaseService airbaseService;
  private final SquadronService squadronService;

  private final Random random = new Random();

  @Inject
  public SquadronDeploymentServiceImpl(final Events events,
                                       final SquadronDeploymentRepository squadronDeploymentRepository,
                                       final AirbaseService airbaseService,
                                       final SquadronService squadronService) {
    this.squadronDeploymentRepository = squadronDeploymentRepository;
    this.airbaseService = airbaseService;
    this.squadronService = squadronService;

    registerEvents(events);
  }

  @Override
  public List<SquadronDeployment> get(Side side) {
    var entities = squadronDeploymentRepository.get(side);

    return SquadronDeploymentMapper.INSTANCE.entitiesToModels(entities);
  }

  private void registerEvents(final Events events) {
    events.getDeploySquadronEvent().register(this::handleDeploySquadronEvent);
  }

  private void handleDeploySquadronEvent(final DeploySquadronEvent deploySquadronEvent) {
    log.info("SquadronDeploymentServiceImpl handle DeploySquadronEvent");

    var airbases = airbaseService.get();

    Side.stream()
        .map(this::get)
        .flatMap(Collection::stream)
        .forEach(deployment -> deploySquadrons(deployment, airbases));
  }

  private void deploySquadrons(final SquadronDeployment deployment, final Map<Id, Airbase> airbases) {
    validateDeployment(deployment, airbases);

    var airbaseIds = deployment.getAirbases();
    var squadronIds = new HashSet<>(deployment.getSquadrons());

    squadronService.get(squadronIds)
        .forEach(squadron -> {
          var airbaseId = pickAirbaseId(airbaseIds);
          var airbase = airbases.get(airbaseId);
          airbase.deploySquadron(squadron);
          log.info("Deploy squadron: '{}' to airbase: '{}'", squadron.getId(), airbase.getId());
        });
  }

  private void validateDeployment(final SquadronDeployment deployment, final Map<Id, Airbase> airbases) {
    var invalidIds = deployment.getAirbases()
        .stream()
        .filter(id -> !airbases.containsKey(id))
        .toList();

    if (!invalidIds.isEmpty()) {
      var invalidIdString = invalidIds.stream()
          .map(Id::toString)
          .collect(Collectors.joining(","));

      throw new GameException("Deployment is not valid " + invalidIdString);
    }
  }

  private Id pickAirbaseId(final List<Id> airbaseIds) {
    var index = random.nextInt(airbaseIds.size());
    return airbaseIds.get(index);
  }
}
