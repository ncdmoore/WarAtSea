package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Region;

public interface RegionService extends BootStrapped {
  Region getAirfieldRegion(Nation nation, String airfieldId);
}
