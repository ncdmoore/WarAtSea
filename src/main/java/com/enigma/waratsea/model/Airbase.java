package com.enigma.waratsea.model;

import com.enigma.waratsea.model.squadron.Squadron;

public interface Airbase {
  Id getId();
  void deploySquadron(Squadron squadron);
}
