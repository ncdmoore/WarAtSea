package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.SquadronDeploymentEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;

public interface SquadronDeploymentRepository {
  List<SquadronDeploymentEntity> get(Side side);
}
