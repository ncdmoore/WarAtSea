package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CapitalShip implements Ship, Airbase {
  private Id id;
  private ShipType type;
  private String title;
  private final boolean airbase = true;
  private Set<Squadron> squadrons;

  @Override
  public Ship commission(final Id id, final String title) {
    this.id = id;
    this.title = title;
    return this;
  }

  @Override
  public void deploySquadron(Squadron squadron) {
    squadrons.add(squadron);
  }
}
