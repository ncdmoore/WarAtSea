package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipAction {
  CARGO_LOADED("Cargo loaded"),
  CARGO_UNLOADED("Cargo unloaded"),

  SHIP_ATTACKED("Ship attacked"),
  SHIP_HULL_DAMAGED("Ship's hull damaged"),
  SHIP_PRIMARY_DAMAGED("Ship's primary gun damaged"),
  SHIP_SECONDARY_DAMAGED("Ship's secondary gun damaged"),
  SHIP_TERTIARY_DAMAGED("Ship's tertiary gun damaged"),
  SHIP_ANTI_AIR_DAMAGED("Ship's anti-air gun damaged"),
  SHIP_TORPEDO_DAMAGED("Ship's torpedo damaged"),
  SHIP_MOVEMENT_REDUCED("Ship's movement reduced"),
  SHIP_DEAD_IN_WATER("Ship dead-in-water"),
  SHIP_OUT_OF_FUEL("Ship out of fuel"),
  SHIP_SUNK("Ship sunk"),

  SHIP_SPOTTED("Ship mpotted"),
  SHIP_LAID_MINEFIELD("Ship laid minefield"),
  SHIP_CLEARED_MINEFIELD("Ship cleared minefield"),

  SHIP_ARRIVED("Ship arrived"),
  SHIP_SAILED("Ship sailed");

  private final String value;
}
