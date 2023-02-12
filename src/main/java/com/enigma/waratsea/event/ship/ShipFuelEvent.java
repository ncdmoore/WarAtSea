package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.FuelAction;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipFuelEvent implements Event {
  private final Ship ship;
  private final FuelAction action;
}

