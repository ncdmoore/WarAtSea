package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;

import java.util.List;

public interface AirfieldService extends BootStrapped {
  List<Airfield> get(List<Id> airfieldId);
  Airfield get(Id airfieldId);
}
