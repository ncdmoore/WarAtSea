package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.model.Die;
import com.enigma.waratsea.service.DiceService;
import com.google.inject.Singleton;

import java.util.Random;

@Singleton
public class DiceServiceImpl implements DiceService {
  private static final int DICE_SIX_SIDED = 6;
  private static final Random DICE = new Random();

  @Override
  public Die get(int sides) {
    return new Die(sides);
  }

  @Override
  public int roll() {
    return DICE.nextInt(DICE_SIX_SIDED) + 1; // returns 1 - 6
  }
}
