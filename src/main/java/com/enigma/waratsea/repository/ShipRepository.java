package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.ShipType;

import java.util.List;

public interface ShipRepository {
  ShipEntity get(Id shipId, ShipType shipType);

  List<ShipRegistryEntity> getRegistry(Side side, ShipType shipType);
}
