package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class ShipRegistry {
  private Id id;
  private String title;
  private Id shipClassId;
  private Set<Squadron> squadrons;
  transient private ShipType shipType;

  public ShipRegistry setShipType(final ShipType shipType) {
    this.shipType = shipType;
    return this;
  }
}
