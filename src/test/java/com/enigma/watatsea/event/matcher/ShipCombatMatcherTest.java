package com.enigma.watatsea.event.matcher;

import com.enigma.waratsea.event.matcher.EnemyMatcher;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.SurfaceShip;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.Constants.ANY_SHIP;
import static com.enigma.waratsea.event.action.ShipAction.SHIP_DAMAGED;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipCombatMatcherTest {

  @Test
  void shouldMatchShip() {
    var shipMatcher = ShipMatcher.builder()
        .side(AXIS)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of(ANY_SHIP))
        .side(ALLIES)
        .build();

    var shipCombatMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_DAMAGED))
        .enemy(enemyMatcher)
        .build();

    var axisShipId = new Id(AXIS, "shipName");

    var axisShip = SurfaceShip.builder()
        .id(axisShipId)
        .build();

    var enemyId = new Id(ALLIES, "shipName");

    var alliedEnemy = Enemy.builder()
        .id(enemyId)
        .type("DESTROYER")
        .build();

    var event = ShipCombatEvent.builder()
        .ship(axisShip)
        .action(SHIP_DAMAGED)
        .enemy(alliedEnemy)
        .build();

    var result = shipCombatMatcher.match(event);

    assertTrue(result);
  }

  @Test
  void shouldNotMatchShipWrongShipType() {
    var shipMatcher = ShipMatcher.builder()
        .side(AXIS)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of("BATTLESHIP"))
        .side(ALLIES)
        .build();

    var shipCombatMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_DAMAGED))
        .enemy(enemyMatcher)
        .build();

    var axisShipId = new Id(AXIS, "shipName");

    var axisShip = SurfaceShip.builder()
        .id(axisShipId)
        .build();

    var enemyId = new Id(ALLIES, "shipName");

    var alliedEnemy = Enemy.builder()
        .id(enemyId)
        .type("DESTROYER")
        .build();

    var event = ShipCombatEvent.builder()
        .ship(axisShip)
        .action(SHIP_DAMAGED)
        .enemy(alliedEnemy)
        .build();

    var result = shipCombatMatcher.match(event);

    assertFalse(result);
  }

  @Test
  void shouldNotMatchShipWrongShipSide() {
    var shipMatcher = ShipMatcher.builder()
        .side(ALLIES)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of("BATTLESHIP"))
        .side(AXIS)
        .build();

    var shipCombatMatcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_DAMAGED))
        .enemy(enemyMatcher)
        .build();

    var axisShipId = new Id(AXIS, "shipName");

    var axisShip = SurfaceShip.builder()
        .id(axisShipId)
        .build();

    var alliedEnemy = new Id(ALLIES, "shipName");

    var enemy = Enemy.builder()
        .id(alliedEnemy)
        .type("DESTROYER")
        .build();

    var event = ShipCombatEvent.builder()
        .ship(axisShip)
        .action(SHIP_DAMAGED)
        .enemy(enemy)
        .build();

    var result = shipCombatMatcher.match(event);

    assertFalse(result);
  }
}
