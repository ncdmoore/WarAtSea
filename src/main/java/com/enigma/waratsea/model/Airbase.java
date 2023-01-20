package com.enigma.waratsea.model;

import com.enigma.waratsea.model.squadron.Squadron;

import java.util.Set;

public interface Airbase {
  Id getId();
  Set<Squadron> getSquadrons();
  void deploySquadron(Squadron squadron);
}
