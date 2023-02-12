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
  public Ship commission(final Commission commission) {
    id = commission.getId();
    title = commission.getTitle();

    nation = Optional.ofNullable(commission.getNation())
        .orElse(nation);

    return this;
  }

  @Override
  public Optional<Cargo> retrieveCargo() {
    return Optional.of(cargo);
  }
}
