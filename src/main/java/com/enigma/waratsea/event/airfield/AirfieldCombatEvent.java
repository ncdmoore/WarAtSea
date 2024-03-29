package com.enigma.waratsea.event.airfield;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.BaseAction;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Enemy;
import lombok.Data;

@Data
public class AirfieldCombatEvent implements Event {
  private final Airfield airfield;
  private final BaseAction action;
  private final Enemy enemy;
}
