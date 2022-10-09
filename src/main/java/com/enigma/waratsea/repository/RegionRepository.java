package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;

public interface RegionRepository {
  List<RegionEntity> get(final Side side, final String mapName);
}
