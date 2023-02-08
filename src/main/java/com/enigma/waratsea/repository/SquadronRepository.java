package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface SquadronRepository {
  SquadronEntity get(Id id);
  void save(String gameId, SquadronEntity squadron);

  void saveManifest(String gameId, Side side, Set<Id> squadronIds);
  List<Id> getManifest(Side side);
}
