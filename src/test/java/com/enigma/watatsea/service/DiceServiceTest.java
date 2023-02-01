package com.enigma.watatsea.service;

import com.enigma.waratsea.service.impl.DiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DiceServiceTest {
  @InjectMocks
  private DiceServiceImpl diceService;

  @Test
  void testGet() {
    var result = diceService.get();

    assertNotNull(result);
  }
}
