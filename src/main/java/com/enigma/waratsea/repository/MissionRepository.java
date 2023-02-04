package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface MissionRepository {
  List<MissionEntity> get(Side side);
  void save(String gameId, Side side, Set<MissionEntity> missions);
}
