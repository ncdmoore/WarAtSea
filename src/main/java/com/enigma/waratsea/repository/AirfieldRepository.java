package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Id;

public interface AirfieldRepository {
  AirfieldEntity get(Id airfieldId);

  void save(String gameId, AirfieldEntity airfield);
}
