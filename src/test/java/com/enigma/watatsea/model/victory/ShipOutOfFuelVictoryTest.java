package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipFuelMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipFuelEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipOutOfFuelVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.SHIP_OUT_OF_FUEL;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipOutOfFuelVictoryTest {
  private Events events;
  private ShipOutOfFuelVictory victoryCondition;

  @BeforeEach
  void setUp() {
    events = new Events();

    var shipMatcher = ShipMatcher.builder()
        .types(Set.of(BATTLESHIP))
        .side(ALLIES)
        .build();

    var victoryMatcher = ShipFuelMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_OUT_OF_FUEL))
        .build();

    victoryCondition = ShipOutOfFuelVictory.builder()
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

    var event = new ShipFuelEvent(ship, SHIP_OUT_OF_FUEL);

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipFuelEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(shipVictoryPoints / 2, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var id = new Id(ALLIES, "battleship");

    var ship = SurfaceShip.builder()
        .id(id)
        .type(BATTLESHIP)
        .build();

    var event = new ShipFuelEvent(ship, SHIP_OUT_OF_FUEL);

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipFuelEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
