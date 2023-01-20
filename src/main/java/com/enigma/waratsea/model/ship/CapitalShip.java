package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;
import java.util.Set;

@Getter
@Builder
public class CapitalShip implements Ship, Airbase {
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
  private Hull hull;
  private Fuel fuel;
  private Movement movement;
  private Set<LandingType> landingType;
  private Catapult catapult;
  private int victoryPoints;
  private Set<Squadron> squadrons;

  @Override
  public Ship commission(final ShipRegistry shipRegistry) {
    id = shipRegistry.getId();
    title = shipRegistry.getTitle();

    nation = Optional.ofNullable(shipRegistry.getNation())
        .orElse(nation);

    shipRegistry.getSquadrons().forEach(this::deploySquadron);
    return this;
  }

  @Override
  public boolean isAirbase() {
    return catapult != null && catapult.getCapacity() > 0;
  }

  @Override
  public void deploySquadron(Squadron squadron) {
    squadrons.add(squadron);
  }
}
