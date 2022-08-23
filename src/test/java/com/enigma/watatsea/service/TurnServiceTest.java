package com.enigma.watatsea.service;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.service.TurnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.TurnType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnServiceTest {
  private TurnService turnService;

  @BeforeEach
  void setUp() {
    turnService = new TurnService();
  }

  @Test
  void nextTurnTest() {
    var currentTurn = Turn.builder()
        .turnType(DAY_1)
        .number(1)
        .build();

    var nextTurn = turnService.next(currentTurn);

    assertEquals(2, nextTurn.getNumber());
    assertEquals(DAY_2, nextTurn.getTurnType());

    nextTurn = turnService.next(nextTurn);

    assertEquals(3, nextTurn.getNumber());
    assertEquals(DAY_3, nextTurn.getTurnType());

    nextTurn = turnService.next(nextTurn);

    assertEquals(4, nextTurn.getNumber());
    assertEquals(TWILIGHT, nextTurn.getTurnType());

    nextTurn = turnService.next(nextTurn);

    assertEquals(5, nextTurn.getNumber());
    assertEquals(NIGHT_1, nextTurn.getTurnType());

    nextTurn = turnService.next(nextTurn);

    assertEquals(6, nextTurn.getNumber());
    assertEquals(NIGHT_2, nextTurn.getTurnType());

    nextTurn = turnService.next(nextTurn);

    assertEquals(7, nextTurn.getNumber());
    assertEquals(DAY_1, nextTurn.getTurnType());
  }
}
