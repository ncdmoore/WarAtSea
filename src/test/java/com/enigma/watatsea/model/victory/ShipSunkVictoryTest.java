package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipSunkVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.SHIP_SUNK;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipSunkVictoryTest {
  private Events events;

  private ShipSunkVictory victoryCondition;

  @BeforeEach
  void setUp() {
    events = new Events();

    var shipMatcher = ShipMatcher.builder()
        .types(Set.of(BATTLESHIP))
        .side(ALLIES)
        .build();

    var victoryMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_SUNK))
        .build();

    victoryCondition = ShipSunkVictory.builder()
        .id("damaged")
        .description("description")
        .matcher(victoryMatcher)
        .build();

    victoryCondition.registerEvents(events);
  }

  @Test
  void shouldIncreaseVictoryPoints() {
    var shipVictoryPoints = 10;

    var id = new Id(ALLIES, "battleship");

    var ship = SurfaceShip.builder()
        .id(id)
        .type(BATTLESHIP)
        .victoryPoints(shipVictoryPoints)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_SUNK)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipCombatEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(shipVictoryPoints, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var shipVictoryPoints = 10;

    var id = new Id(AXIS, "battleship");

    var ship = SurfaceShip.builder()
        .id(id)
        .type(BATTLESHIP)
        .victoryPoints(shipVictoryPoints)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_SUNK)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipCombatEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
