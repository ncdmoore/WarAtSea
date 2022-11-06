package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.AircraftEntity;
import com.enigma.waratsea.model.Id;

public interface AircraftRepository {
  AircraftEntity get(Id aircraftId);
}
