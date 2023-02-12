package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.CombatAction;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipCombatEvent implements Event {
  private final Ship ship;
  private final CombatAction action;
}

