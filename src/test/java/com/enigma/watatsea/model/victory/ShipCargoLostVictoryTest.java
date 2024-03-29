package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.Cargo;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipCargoLostVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.SHIP_SUNK;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.DESTROYER;
import static com.enigma.waratsea.model.ship.ShipType.HEAVY_CRUISER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipCargoLostVictoryTest {
  private Events events;
  private ShipCargoLostVictory victoryCondition;

  @BeforeEach
  void setUp() {
    events = new Events();

    var shipMatcher = ShipMatcher.builder()
        .side(AXIS)
        .build();

    var victoryMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_SUNK))
        .build();

    victoryCondition = ShipCargoLostVictory.builder()
        .id("cargo-lost")
        .description("description")
        .matcher(victoryMatcher)
        .build();

    victoryCondition.registerEvents(events);
  }

  @Test
  void shouldIncreaseVictoryPoints() {
    var level = 3;

    var id = new Id(AXIS, "heavy-cruiser");

    var cargo = Cargo.builder()
        .capacity(3)
        .level(level)
        .build();

    var ship = SurfaceShip.builder()
        .id(id)
        .type(HEAVY_CRUISER)
        .cargo(cargo)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_SUNK)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    events.getShipCombatEvent().fire(event);

    var postPoints = victoryCondition.getTotalPoints();

    var awardedPoints = victoryCondition.getFactor() * level;

    assertEquals(0, prePoints);
    assertEquals(awardedPoints, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var level = 0;

    var id = new Id(AXIS, "destroyer");

    var cargo = Cargo.builder()
        .capacity(3)
        .level(level)
        .build();

    var ship = SurfaceShip.builder()
        .id(id)
        .type(DESTROYER)
        .cargo(cargo)
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
