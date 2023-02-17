package com.enigma.waratsea.event.port;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.event.action.BaseAction;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.port.Port;
import lombok.Data;

@Data
public class PortCombatEvent implements Event {
  private final Port port;
  private final BaseAction action;
  private Enemy enemy;
}
