package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface PortService extends BootStrapped {
  List<Port> get(List<Id> portIds);
  Set<Port> get(Side side);
  Port get(Id portId);
}
