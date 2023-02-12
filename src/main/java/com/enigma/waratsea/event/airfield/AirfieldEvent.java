package com.enigma.waratsea.event.airfield;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.BaseAction;
import com.enigma.waratsea.model.Airfield;
import lombok.Data;

@Data
public class AirfieldEvent implements Event {
  private final Airfield airfield;
  private final BaseAction action;
}
