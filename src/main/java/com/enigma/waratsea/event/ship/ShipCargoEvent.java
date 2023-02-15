package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.CargoAction;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipCargoEvent implements Event {
  private final Ship ship;
  private final CargoAction action;
  private final int cargoLevel;
  private final Port originPort;
  private final Port destinationPort;
}

