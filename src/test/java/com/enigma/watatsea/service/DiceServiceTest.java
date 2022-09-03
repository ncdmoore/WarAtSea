package com.enigma.watatsea.service;

import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.DiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceServiceTest {
  private DiceService diceService;

  @BeforeEach
  void setUp() {
    diceService = new DiceServiceImpl();
  }

  @Test
  void testRoll() {
    var result = diceService.roll();

    assertTrue(result <= 6);
    assertTrue(result >= 1);
  }
}
