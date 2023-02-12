package com.enigma.waratsea.event.squadron;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.ContactAction;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Data;

@Data
public class SquadronContactEvent implements Event {
  private final Squadron squadron;
  private final ContactAction action;
}

