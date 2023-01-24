package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.model.Id;

public interface SquadronAllotmentRepository {
  AllotmentEntity get(String timeFrame, Id allotmentId);
}
