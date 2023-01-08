package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
public class SurfaceShip implements Ship {
  @Getter
  private Id id;

  @Getter
  private ShipType type;

  @Getter
  private String title;

  @Getter
  private final boolean airbase = false;

  @Override
  public Ship commission(final Id id, final String title) {
    this.id = id;
    this.title = title;
    return this;
  }
}
