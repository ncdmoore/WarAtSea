package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.Ship;

import java.util.Set;

public interface ShipService extends BootStrapped {
  Set<Ship> get(Set<Id> shipIds);
  Ship get(Id shipId);
}
