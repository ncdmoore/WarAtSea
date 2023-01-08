package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.model.Id;

public interface SquadronRepository {
  SquadronEntity get(Id id);
  void save(String gameId, SquadronEntity squadron);
}
