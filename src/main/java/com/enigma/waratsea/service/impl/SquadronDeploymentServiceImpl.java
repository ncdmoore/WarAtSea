package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.mapper.SquadronDeploymentMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.SquadronDeployment;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
import com.enigma.waratsea.service.SquadronDeploymentService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Singleton
public class SquadronDeploymentServiceImpl implements SquadronDeploymentService {
  private final SquadronDeploymentRepository squadronDeploymentRepository;


  @Inject
  public SquadronDeploymentServiceImpl(final SquadronDeploymentRepository squadronDeploymentRepository) {
    this.squadronDeploymentRepository = squadronDeploymentRepository;
  }

  @Override
  public List<SquadronDeployment> get(Side side) {
    var entities = squadronDeploymentRepository.get(side);

    return SquadronDeploymentMapper.INSTANCE.entitiesToModels(entities);
  }
}
