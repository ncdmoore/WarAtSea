package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ShipRegistry {
  private Id id;
  private String title;
  private Id shipClassId;
  transient private ShipType shipType;

  public ShipRegistry setShipType(final ShipType shipType) {
    this.shipType = shipType;
    return this;
  }
}
