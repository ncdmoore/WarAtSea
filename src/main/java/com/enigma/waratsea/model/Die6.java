package com.enigma.waratsea.model;

import java.util.Random;

public class Die6 implements Die {
  private final static Random random= new Random();

  @Override
  public int roll() {
    int sides = 6;
    return random.nextInt(sides) + 1;
  }
}
