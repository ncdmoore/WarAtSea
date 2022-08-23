package com.enigma.watatsea.model;

import com.enigma.waratsea.model.Side;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SideTest {
  @Test
  void testOpposite() {
    assertEquals(Side.ALLIES, Side.AXIS.oppositeSide());
    assertEquals(Side.AXIS, Side.ALLIES.oppositeSide());
  }

  @Test
  void testFriendlies() {
    assertEquals(List.of(Side.ALLIES, Side.NEUTRAL), Side.ALLIES.getFriendlySides());
    assertEquals(List.of(Side.AXIS, Side.NEUTRAL), Side.AXIS.getFriendlySides());
  }
}
