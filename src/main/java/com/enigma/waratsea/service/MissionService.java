package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.mission.Mission;

import java.util.Set;

public interface MissionService extends BootStrapped {
  Set<Mission> get(Side side);
}
