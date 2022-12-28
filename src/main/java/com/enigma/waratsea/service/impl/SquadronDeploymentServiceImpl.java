package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.DeploySquadronEvent;
import com.enigma.waratsea.mapper.SquadronDeploymentMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.SquadronDeployment;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.SquadronDeploymentService;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SquadronDeploymentServiceImpl implements SquadronDeploymentService {
  private final SquadronDeploymentRepository squadronDeploymentRepository;
  private final SquadronService squadronService;
  private final GameService gameService;

  @Inject
  public SquadronDeploymentServiceImpl(final Events events,
                                       final SquadronDeploymentRepository squadronDeploymentRepository,
                                       final SquadronService squadronService,
                                       final GameService gameService) {
    this.squadronDeploymentRepository = squadronDeploymentRepository;
    this.squadronService = squadronService;
    this.gameService = gameService;

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

    Side.stream()
        .map(this::get)
        .flatMap(Collection::stream)
        .forEach(this::deploySquadron);
  }

  private void deploySquadron(final SquadronDeployment deployment) {
    log.debug("airbase: '{}'", deployment.getAirbase());
    log.debug("squadrons: '{}", deployment.getSquadrons().stream().map(Id::toString).collect(Collectors.joining(",")));

    var airbaseId = deployment.getAirbase();

    var airbase = gameService.getGame()
        .getPlayers()
        .get(airbaseId.getSide())
        .getAirbases()
        .get(airbaseId);

    var squadronIds = new HashSet<>(deployment.getSquadrons());

    squadronService.get(squadronIds)
        .forEach(airbase::deploySquadron);
  }
}
