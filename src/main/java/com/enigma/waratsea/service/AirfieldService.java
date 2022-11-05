package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface AirfieldService extends BootStrapped {
  List<Airfield> get(List<Id> airfieldIds);
  Set<Airfield> get(Side side);
  Airfield get(Id airfieldId);
}
