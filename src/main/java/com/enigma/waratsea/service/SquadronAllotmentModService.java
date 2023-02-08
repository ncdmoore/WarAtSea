package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.option.AllotmentModification;

import java.util.Set;

public interface SquadronAllotmentModService extends BootStrapped {
  Set<AllotmentModification> get(NationId modificationId);
}
