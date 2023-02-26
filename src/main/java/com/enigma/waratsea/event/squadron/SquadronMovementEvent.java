package com.enigma.waratsea.event.squadron;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.SquadronAction;
import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Data;

@Data
public class SquadronMovementEvent implements Event {
  private final Squadron squadron;
  private final SquadronAction action;
  private final Airbase airbase;
}
