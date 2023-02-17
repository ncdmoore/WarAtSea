package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.ShipAction;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipMovementEvent implements Event {
  private final Ship ship;
  private final ShipAction action;
  private final Port port;
}

