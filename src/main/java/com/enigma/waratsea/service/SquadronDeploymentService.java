package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.SquadronDeployment;

import java.util.List;

public interface SquadronDeploymentService {
  List<SquadronDeployment> get(Side side);
}
