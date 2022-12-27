package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.squadron.Squadron;

import java.util.Set;

public interface SquadronService extends BootStrapped {
  Set<Squadron> get(Side side);
  Squadron get(Id squadronId);
}
