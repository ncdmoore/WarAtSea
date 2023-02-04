package com.enigma.watatsea.model;

import com.enigma.waratsea.model.turn.Turn;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_2;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_3;
import static com.enigma.waratsea.model.turn.TimeRange.NIGHT_1;
import static com.enigma.waratsea.model.turn.TimeRange.NIGHT_2;
import static com.enigma.waratsea.model.turn.TimeRange.TWILIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnTest {
  private final LocalDate date = LocalDate.of(1940, 1, 1);

  @Test
  void testNextTimeRangeDay_1() {
    var turn = Turn.builder().timeRange(DAY_1).number(1).date(date).build();

    var nextTurn = turn.next();

    assertEquals(2, nextTurn.getNumber());
    assertEquals(DAY_2, nextTurn.getTimeRange());
    assertEquals(date, nextTurn.getDate());
  }

  @Test
  void testNextTimeRangeDay_2() {
    var turn = Turn.builder().timeRange(DAY_2).number(2).date(date).build();

    var nextTurn = turn.next();

    assertEquals(3, nextTurn.getNumber());
    assertEquals(DAY_3, nextTurn.getTimeRange());
    assertEquals(date, nextTurn.getDate());
  }

  @Test
  void testNextTimeRangeDay_3() {
    var turn = Turn.builder().timeRange(DAY_3).number(3).date(date).build();

    var nextTurn = turn.next();

    assertEquals(4, nextTurn.getNumber());
    assertEquals(TWILIGHT, nextTurn.getTimeRange());
    assertEquals(date, nextTurn.getDate());
  }

  @Test
  void testNextTimeRangeTwilight() {
    var turn = Turn.builder().timeRange(TWILIGHT).number(4).date(date).build();

    var nextTurn = turn.next();

    assertEquals(5, nextTurn.getNumber());
    assertEquals(NIGHT_1, nextTurn.getTimeRange());
    assertEquals(date, nextTurn.getDate());
  }

  @Test
  void testNextTimeRangeNight_1() {
    var turn = Turn.builder().timeRange(NIGHT_1).number(5).date(date).build();

    var nextTurn = turn.next();

    assertEquals(6, nextTurn.getNumber());
    assertEquals(NIGHT_2, nextTurn.getTimeRange());
    assertEquals(date, nextTurn.getDate());
  }

  @Test
  void testNextTimeRangeNight_2() {
    var turn = Turn.builder().timeRange(NIGHT_2).number(6).date(date).build();

    var nextTurn = turn.next();

    assertEquals(7, nextTurn.getNumber());
    assertEquals(DAY_1, nextTurn.getTimeRange());
    assertEquals(date.plusDays(1), nextTurn.getDate());
  }
}
