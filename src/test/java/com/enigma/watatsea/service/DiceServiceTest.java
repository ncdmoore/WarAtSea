package com.enigma.watatsea.service;

import com.enigma.waratsea.service.DiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DiceServiceTest {
  @InjectMocks
  private DiceServiceImpl diceService;

  @Test
  void testRoll() {
    var result = diceService.roll();

    assertTrue(result <= 6);
    assertTrue(result >= 1);
  }
}
