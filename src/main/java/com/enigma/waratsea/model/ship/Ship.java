package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.dto.ArmourDto;
import com.enigma.waratsea.dto.WeaponsDto;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;

import java.util.Optional;

public interface Ship extends Comparable<Ship> {
  Id getId();

  Id getShipClassId();

  ShipType getType();

  String getTitle();

  Nation getNation();

  String getShipClass();

  WeaponsDto getWeapons();

  ArmourDto getArmour();

  Movement getMovement();

  Fuel getFuel();

  Ship commission(Commission commission);

  Optional<Cargo> retrieveCargo();

  int getVictoryPoints();
}
