package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCargoMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCargoEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.ship.Cargo;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipCargoUnloadedVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.CARGO_UNLOADED;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.HEAVY_CRUISER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipCargoUnloadedVictoryTest {
  private Events events;
  private ShipCargoUnloadedVictory victoryConditionBasicPoints;
  private ShipCargoUnloadedVictory victoryConditionFactor;

  private static final int POINTS_AWARDED = 8;
  private static final int FACTOR = 4;

  @BeforeEach
  void setUp() {
    events = new Events();

    var shipMatcher = ShipMatcher.builder()
        .side(AXIS)
        .build();

    var victoryMatcher = ShipCargoMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(CARGO_UNLOADED))
        .build();

    victoryConditionBasicPoints = ShipCargoUnloadedVictory.builder()
        .id("cargo-unloaded-basic-points")
        .description("description")
        .points(POINTS_AWARDED)
        .matcher(victoryMatcher)
        .build();

    victoryConditionFactor = ShipCargoUnloadedVictory.builder()
        .id("cargo-unloaded-factor")
        .description("description")
        .factor(FACTOR)
        .matcher(victoryMatcher)
        .build();

    victoryConditionBasicPoints.registerEvents(events);
    victoryConditionFactor.registerEvents(events);
  }

  @Test
  void shouldIncreaseVictoryPointsWithBasicPoints() {
    var level = 3;

    var shipId = new Id(AXIS, "heavy-cruiser");

    var cargo = Cargo.builder()
        .capacity(3)
        .level(level)
        .build();

    var ship = SurfaceShip.builder()
        .id(shipId)
        .type(HEAVY_CRUISER)
        .cargo(cargo)
        .build();

    var originPortId = new Id(AXIS, "origin-port");
    var destinationPortId = new Id(AXIS, "destination-port");

    var originPort = Port.builder()
        .id(originPortId)
        .build();

    var destPort = Port.builder()
        .id(destinationPortId)
        .build();

    var event = new ShipCargoEvent(ship, CARGO_UNLOADED, 3, originPort, destPort);

    var prePoints = victoryConditionBasicPoints.getTotalPoints();

    events.getShipCargoEvent().fire(event);

    var postPoints = victoryConditionBasicPoints.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(POINTS_AWARDED, postPoints);
  }

  @Test
  void shouldIncreaseVictoryPointsWithFactor() {
    var level = 3;

    var shipId = new Id(AXIS, "heavy-cruiser");

    var cargo = Cargo.builder()
        .capacity(3)
        .level(level)
        .build();

    var ship = SurfaceShip.builder()
        .id(shipId)
        .type(HEAVY_CRUISER)
        .cargo(cargo)
        .build();

    var originPortId = new Id(AXIS, "origin-port");
    var destinationPortId = new Id(AXIS, "destination-port");

    var originPort = Port.builder()
        .id(originPortId)
        .build();

    var destPort = Port.builder()
        .id(destinationPortId)
        .build();

    var event = new ShipCargoEvent(ship, CARGO_UNLOADED, 3, originPort, destPort);

    var prePoints = victoryConditionFactor.getTotalPoints();

    events.getShipCargoEvent().fire(event);

    var postPoints = victoryConditionFactor.getTotalPoints();

    var expected = level * FACTOR;

    assertEquals(0, prePoints);
    assertEquals(expected, postPoints);
  }
}
