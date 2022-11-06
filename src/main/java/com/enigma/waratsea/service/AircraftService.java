package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;

public interface AircraftService extends BootStrapped {
  Aircraft get(Id aircraftId);
}
