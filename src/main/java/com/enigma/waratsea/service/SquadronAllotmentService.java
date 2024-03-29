package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.dto.AllotmentModificationDto;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.squadron.Allotment;

import java.util.Optional;

public interface SquadronAllotmentService extends BootStrapped {
  Optional<Allotment> get(Scenario scenario, NationId allotmentId);
  void update(AllotmentModificationDto dto);
}
