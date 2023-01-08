package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

@Builder
public class CapitalShip implements Ship, Airbase {
  @Getter
  private Id id;

  @Getter
  private ShipType type;

  @Getter
  private String title;

  @Getter
  private final boolean airbase = true;

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
