package com.enigma.waratsea.model.die;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Die6 implements Die {
  private final static Random random= new Random();

  @Override
  public int roll() {
    int sides = 6;
    var result = random.nextInt(sides) + 1;

    log.info("Rolled die, result = '{}'", result);

    return result;
  }
}
