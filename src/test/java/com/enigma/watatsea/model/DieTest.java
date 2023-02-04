package com.enigma.watatsea.model;

import com.enigma.waratsea.model.die.Die;
import com.enigma.waratsea.model.die.Die6;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DieTest {
  private final Die die = new Die6();

  @Test
  void shouldGetValueBetweenOneAndSix() {
    var result = die.roll();

    assertTrue(result >= 1);
    assertTrue(result <= 6);
  }
}
