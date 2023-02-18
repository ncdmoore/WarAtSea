package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.ShipAction;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipCombatEvent implements Event {
  private final Ship ship;
  private final ShipAction action;
  private final Enemy enemy;
}

