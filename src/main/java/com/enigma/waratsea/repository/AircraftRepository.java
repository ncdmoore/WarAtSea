package com.enigma.waratsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;

public interface AircraftRepository {
  Aircraft get(Id aircraftId);
}
