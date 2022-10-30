package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Side;

import java.util.Set;

public interface Player {
  Side getSide();

  void setAirfields(final Set<Airfield> airfields);
}
