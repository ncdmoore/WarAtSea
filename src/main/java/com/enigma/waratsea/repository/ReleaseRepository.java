package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.release.ReleaseEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface ReleaseRepository {
  List<ReleaseEntity> get(Side side);
  void save(String gameId, Side side, Set<ReleaseEntity> releaseConditions);
}
