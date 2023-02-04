package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.entity.ship.FuelEntity;
import com.enigma.waratsea.entity.ship.MovementEntity;
import com.enigma.waratsea.entity.ship.TorpedoEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class Submarine implements Ship {
  private Id id;
  private ShipType type;
  private String title;
  private String shipClass;
  private Nation nation;
  private TorpedoEntity torpedo;
  private MovementEntity movement;
  private FuelEntity fuel;
  private int victoryPoints;

  @Override
  public Ship commission(Commission commission) {
    id = commission.getId();
    title = commission.getTitle();

    nation = Optional.ofNullable(commission.getNation())
        .orElse(nation);

    return this;
  }

  @Override
  public boolean isAirbase() {
    return false;
  }
}
