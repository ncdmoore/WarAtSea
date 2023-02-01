package com.enigma.waratsea.model;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class Die {
  private final static Random random= new Random();
  private final int sides;

  public int roll() {
    return random.nextInt(sides) + 1;
  }
}
