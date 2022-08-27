package com.enigma.waratsea.service;

import com.google.inject.Singleton;

import java.util.Random;

@Singleton
public class DiceService {
  private static final int DICE_SIX_SIDED = 6;
  private static final Random DICE = new Random();

  public int roll() {
    return DICE.nextInt(DICE_SIX_SIDED) + 1; // returns 1 - 6
  }
}
