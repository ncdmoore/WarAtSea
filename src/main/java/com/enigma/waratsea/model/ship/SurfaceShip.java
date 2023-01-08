package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SurfaceShip implements Ship {
  private Id id;
  private ShipType type;
  private String title;
  private final boolean airbase = false;

  @Override
  public Ship commission(final Id id, final String title) {
    this.id = id;
    this.title = title;
    return this;
  }
}
