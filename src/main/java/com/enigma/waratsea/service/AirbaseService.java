package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Id;

import java.util.Map;

public interface AirbaseService {
  Map<Id, Airbase> get();
}
