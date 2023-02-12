package com.enigma.waratsea.event.ship;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.ContactAction;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Data;

@Data
public class ShipContactEvent implements Event {
  private final Ship ship;
  private final ContactAction action;
}

