package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class SurfaceShip implements Ship {
  private Id id;
  private ShipType type;
  private String title;
  private String shipClass;
  private final boolean airbase = false;
  private Nation nation;
  private Gun primary;
  private Gun secondary;
  private Gun tertiary;
  private Gun antiAir;
  private Torpedo torpedo;
  private boolean asw;
  private Hull hull;
  private Fuel fuel;
  private Movement movement;
  private Cargo cargo;
  private int victoryPoints;

  @Override
  public Ship commission(final ShipRegistry shipRegistry) {
    id = shipRegistry.getId();
    title = shipRegistry.getTitle();

    nation = Optional.ofNullable(shipRegistry.getNation())
        .orElse(nation);

    return this;
  }
}
