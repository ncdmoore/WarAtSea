package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipSunkVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.CombatAction.SHIP_SUNK;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipSunkVictoryTest {
  private ShipSunkVictory victoryCondition;

  @BeforeEach
  void setUp() {
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

    var event = new ShipCombatEvent(ship, SHIP_SUNK);

    var dto = VictoryDto.builder()
        .shipCombatEvent(event)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleEvent(dto);

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

    var event = new ShipCombatEvent(ship, SHIP_SUNK);

    var dto = VictoryDto.builder()
        .shipCombatEvent(event)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleEvent(dto);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
