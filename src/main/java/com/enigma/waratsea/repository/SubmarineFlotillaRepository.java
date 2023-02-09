package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface SubmarineFlotillaRepository {
  List<SubmarineFlotillaEntity> get(Side side);
  void save(String gameId, Side side, Set<SubmarineFlotillaEntity> flotillas);
}
