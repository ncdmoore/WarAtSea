package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;

import java.util.Optional;

public interface Ship {
  Id getId();
  ShipType getType();
  String getTitle();
  Nation getNation();

  Ship commission(Commission commission);

  Optional<Cargo> retrieveCargo();

  int getVictoryPoints();
}
