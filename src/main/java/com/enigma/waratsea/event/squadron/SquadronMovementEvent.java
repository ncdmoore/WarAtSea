package com.enigma.waratsea.event.squadron;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.MovementAction;
import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Data;

@Data
public class SquadronMovementEvent implements Event {
  private final Squadron squadron;
  private final MovementAction action;
  private final Airbase airbase;
}
