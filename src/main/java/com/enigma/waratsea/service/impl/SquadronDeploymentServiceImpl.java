package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadSquadronEvent;
import com.enigma.waratsea.mapper.SquadronDeploymentMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.squadron.SquadronDeployment;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
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


  @Inject
  public SquadronDeploymentServiceImpl(final Events events,
                                       final SquadronDeploymentRepository squadronDeploymentRepository,
                                       final SquadronService squadronService) {
    this.squadronDeploymentRepository = squadronDeploymentRepository;
    this.squadronService = squadronService;

    registerEvents(events);
  }

  @Override
  public List<SquadronDeployment> get(Side side) {
    var entities = squadronDeploymentRepository.get(side);

    return SquadronDeploymentMapper.INSTANCE.entitiesToModels(entities);
  }

  private void registerEvents(final Events events) {
    events.getLoadSquadronEvent().register(this::handleLoadSquadronEvent);
  }

  private void handleLoadSquadronEvent(final LoadSquadronEvent loadSquadronEvent) {
    log.info("SquadronDeploymentServiceImpl handle LoadSquadron Event");

    Side.stream().map(this::get).flatMap(Collection::stream).forEach(this::deploySquadron);
  }

  private void deploySquadron(final SquadronDeployment deployment) {
    log.info("airbase: '{}'", deployment.getAirbase());
    log.info("squadrons: '{}", deployment.getSquadrons().stream().map(Id::toString).collect(Collectors.joining(",")));

    var airbase = deployment.getAirbase();
    var squadronIds = new HashSet<>(deployment.getSquadrons());
    var squadrons = squadronService.get(squadronIds);

    log.info("Found squadrons: '{}'", squadrons.stream().map(Squadron::getId).map(Id::toString).collect(Collectors.joining(",")));
  }
}
