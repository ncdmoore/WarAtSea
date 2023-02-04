package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.map.Region;
import com.enigma.waratsea.model.Side;

import java.util.Set;

public interface RegionService extends BootStrapped {
  Region getAirfieldRegion(Nation nation, Id airfieldId);
  Set<Nation> getNations(Side side);
}
