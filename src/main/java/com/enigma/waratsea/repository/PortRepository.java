package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.model.Id;

public interface PortRepository {
  PortEntity get(Id portId);

  void save(String gameId, PortEntity port);
}
