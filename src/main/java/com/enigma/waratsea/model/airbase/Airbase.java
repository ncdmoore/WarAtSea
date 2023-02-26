package com.enigma.waratsea.model.airbase;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.squadron.Squadron;

import java.util.Set;

public interface Airbase {
  Id getId();

  String getTitle();

  Set<Nation> getNations();

  Set<Squadron> getSquadrons();

  void deploySquadron(Squadron squadron);

  boolean isOperational();
}
