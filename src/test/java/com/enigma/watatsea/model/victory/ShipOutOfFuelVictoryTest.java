package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.ShipFuelMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipFuelEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.victory.ShipOutOfFuelVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.FuelAction.OUT_OF_FUEL;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipOutOfFuelVictoryTest {
  private ShipOutOfFuelVictory victoryCondition;

  @BeforeEach
  void setUp() {
    var shipMatcher = ShipMatcher.builder()
        .types(Set.of(BATTLESHIP))
        .side(ALLIES)
        .build();

    var victoryMatcher = ShipFuelMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(OUT_OF_FUEL))
        .build();

    victoryCondition = ShipOutOfFuelVictory.builder()
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

    var event = new ShipFuelEvent(ship, OUT_OF_FUEL);

    var dto = VictoryDto.builder()
        .shipFuelEvent(event)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleEvent(dto);

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

    var event = new ShipFuelEvent(ship, OUT_OF_FUEL);

    var dto = VictoryDto.builder()
        .shipFuelEvent(event)
        .build();

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleEvent(dto);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
