package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.SubmarineFlotilla;

import java.util.Set;

public interface SubmarineFlotillaService extends BootStrapped {
  Set<SubmarineFlotilla> get(Side side);
  Set<SubmarineFlotilla> get(Set<Id> flotillaIds);
}
