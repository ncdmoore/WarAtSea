package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MotorTorpedoBoat implements Ship {
  private Id id;
  private ShipType type;
  private String title;
  private String shipClass;
  private final boolean airbase = false;
  private Nation nation;
  private Torpedo torpedo;
  private Movement movement;
  private Fuel fuel;
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
    return Optional.empty();
  }
}
