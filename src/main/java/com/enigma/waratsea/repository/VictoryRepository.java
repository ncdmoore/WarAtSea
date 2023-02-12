package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.victory.VictoryEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface VictoryRepository {
  List<VictoryEntity> get(Side side);
  void save(String gameId, Side side, Set<VictoryEntity> victoryConditions);
}
