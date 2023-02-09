package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.model.Side;

import java.util.Set;

public interface MtbFlotillaService extends BootStrapped {
  Set<MtbFlotilla> get(Side side);
  Set<MtbFlotilla> get(Set<Id> flotillaIds);
}
