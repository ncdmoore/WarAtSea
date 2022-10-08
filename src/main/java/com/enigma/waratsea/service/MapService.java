package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.GameMap;

public interface MapService extends BootStrapped {
  GameMap get();
}
