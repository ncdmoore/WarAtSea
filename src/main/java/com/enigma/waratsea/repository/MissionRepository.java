package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;

public interface MissionRepository {
  List<MissionEntity> get(Side side);
}
