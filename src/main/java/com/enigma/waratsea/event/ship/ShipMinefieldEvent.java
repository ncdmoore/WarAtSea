package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.MinefieldAction;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipMinefieldEvent implements Event {
  private final Ship ship;
  private final MinefieldAction action;
}

