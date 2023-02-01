package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.model.Die;
import com.enigma.waratsea.model.Die6;
import com.enigma.waratsea.service.DiceService;
import com.google.inject.Singleton;

@Singleton
public class DiceServiceImpl implements DiceService {
  @Override
  public Die get() {
    return new Die6();
  }
}
