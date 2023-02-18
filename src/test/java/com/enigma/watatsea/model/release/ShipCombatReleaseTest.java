package com.enigma.watatsea.model.release;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.EnemyMatcher;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.release.ShipCombatRelease;
import com.enigma.waratsea.model.ship.CapitalShip;
import com.enigma.waratsea.model.taskForce.TaskForce;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.ShipAction.SHIP_ATTACKED;
import static com.enigma.waratsea.model.AssetState.ACTIVE;
import static com.enigma.waratsea.model.AssetState.RESERVE;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLECRUISER;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipCombatReleaseTest {
  private Events events;

  @BeforeEach
  void setUp() {
    events = new Events();
  }

  @Test
  void shouldReleaseTaskForce() {
    var taskForce = TaskForce.builder()
        .state(RESERVE)
        .build();

    var shipMatcher = ShipMatcher.builder()
        .types(Set.of(BATTLESHIP, BATTLECRUISER))
        .side(ALLIES)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of("BATTLESHIP", "BATTLECRUISER", "HEAVY_CRUISER"))
        .side(AXIS)
        .build();

    var matcher = ShipCombatMatcher.builder()
        .ship(shipMatcher)
        .actions(Set.of(SHIP_ATTACKED))
        .enemy(enemyMatcher)
        .build();

    var shipAttackedRelease = ShipCombatRelease.builder()
        .taskForces(Set.of(taskForce))
        .matcher(matcher)
        .build();

    shipAttackedRelease.registerEvents(events);

    var shipId = new Id(ALLIES, "shipName");

    var ship = CapitalShip.builder()
        .id(shipId)
        .type(BATTLECRUISER)
        .build();

    var enemyId = new Id(AXIS, "heavy-cruiser");

    var enemy = Enemy.builder()
        .type("HEAVY_CRUISER")
        .id(enemyId)
        .build();

    var event = ShipCombatEvent.builder()
        .ship(ship)
        .action(SHIP_ATTACKED)
        .enemy(enemy)
        .build();

    var preState = taskForce.getState();

    assertEquals(RESERVE, preState);

    events.getShipCombatEvent().fire(event);

    var postState = taskForce.getState();

    assertEquals(ACTIVE, postState);
  }
}
