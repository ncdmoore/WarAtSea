package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.airbase.Airbase;

import java.util.Map;
import java.util.Set;

public interface AirbaseService extends BootStrapped {
  Set<Nation> getNations(Side side);
  Map<Id, Airbase> get();
}
