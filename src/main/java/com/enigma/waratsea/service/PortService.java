package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.port.Port;

import java.util.Set;

public interface PortService extends BootStrapped {
  Set<Port> get(Set<Id> portIds);
  Set<Port> get(Side side);
  Port get(Id portId);
}
