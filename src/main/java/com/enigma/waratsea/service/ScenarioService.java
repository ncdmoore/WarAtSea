package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Scenario;

import java.util.List;

public interface ScenarioService {
  List<Scenario> get();
  Scenario get(int id);
}
