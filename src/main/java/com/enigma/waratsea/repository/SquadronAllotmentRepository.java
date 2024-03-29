package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.model.NationId;

import java.util.Optional;

public interface SquadronAllotmentRepository {
  Optional<AllotmentEntity> get(String timeFrame, NationId allotmentId);
}
