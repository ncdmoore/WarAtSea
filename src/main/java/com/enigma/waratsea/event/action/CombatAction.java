package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CombatAction {
  AIRFIELD_ATTACKED("Airfield attacked"),
  AIRFIELD_DAMAGED("Airfield damaged"),

  PORT_ATTACKED("Port attacked"),
  PORT_DAMAGED("Port damaged"),

  SHIP_ATTACKED("Ship attacked"),
  SHIP_HULL_DAMAGED("Ship's hull damaged"),
  SHIP_PRIMARY_DAMAGED("Ship's primary gun damaged"),
  SHIP_SECONDARY_DAMAGED("Ship's secondary gun damaged"),
  SHIP_TERTIARY_DAMAGED("Ship's tertiary gun damaged"),
  SHIP_ANTI_AIR_DAMAGED("Ship's anti-air gun damaged"),
  SHIP_TORPEDO_DAMAGED("Ship's torpedo damaged"),
  SHIP_MOVEMENT_REDUCED("Ship's movement reduced"),
  SHIP_DEAD_IN_WATER("Ship dead-in-water"),
  SHIP_SUNK("Ship sunk"),

  SQUADRON_ATTACKED("Squadron attacked"),
  SQUADRON_DAMAGED("Squadron damaged"),
  SQUADRON_DESTROYED("Squadron destroyed");

  private final String value;
}
