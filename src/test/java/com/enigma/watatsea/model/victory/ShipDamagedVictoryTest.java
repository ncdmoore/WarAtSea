package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipDamagedVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.SHIP_HULL_DAMAGED;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipDamagedVictoryTest {
  private Events events;
  private ShipDamagedVictory victoryCondition;

  private static final int POINTS_AWARDED = 5;

  @BeforeEach
  void setUp() {
    events = new Events();

    var shipMatcher = ShipMatcher.builder()
        .types(Set.of(BATTLESHIP))
        .side(ALLIES)
        .build();

    var victoryMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_HULL_DAMAGED))
        .build();

    victoryCondition = ShipDamagedVictory.builder()
        .id("damaged")
        .description("description")
        .points(POINTS_AWARDED)
        .matcher(victoryMatcher)
        .build();

    victoryCondition.registerEvents(events);
  }

  @Test
  void shouldIncreaseVictoryPoints() {
    var id = new Id(ALLIES, "battleship");

    var ship = SurfaceShip.builder()
        .id(id)
        .type(BATTLESHIP)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_HULL_DAMAGED)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipCombatEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(prePoints + POINTS_AWARDED, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var id = new Id(AXIS, "battleship");

    var ship = SurfaceShip.builder()
        .id(id)
        .type(BATTLESHIP)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_HULL_DAMAGED)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipCombatEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
