package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.dto.CargoDto;
import com.enigma.waratsea.model.Id;

public interface Ship {
  Id getId();
  ShipType getType();
  String getTitle();

  Ship commission(Commission commission);

  boolean isAirbase();

  void loadCargo(CargoDto cargo);
}
