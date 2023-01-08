package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
public class AircraftCarrier implements Ship, Airbase {
  @Getter
  private Id id;

  @Getter
  private ShipType type;

  @Getter
  private String title;

  @Getter
  private Nation nation;

  @Getter
  private final boolean airbase = true;

  private FlightDeck flightDeck;
  private Gun secondary;
  private Gun tertiary;
  private Gun antiAir;
  private Torpedo torpedo;
  private Hull hull;
  private Fuel fuel;
  private Movement movement;
  private Set<LandingType> landingType;
  private int victoryPoints;
  private Set<Squadron> squadrons;

  @Override
  public Ship commission(final Id id, final String title) {
    this.id = id;
    this.title = title;
    return this;
  }

  @Override
  public void deploySquadron(Squadron squadron) {

  }
}
