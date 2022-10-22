package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.model.Id;

import java.util.List;

public interface RegionRepository {
  List<RegionEntity> get(Id mapId);
}
