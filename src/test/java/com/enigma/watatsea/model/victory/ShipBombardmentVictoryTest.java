package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.airfield.AirfieldCombatEvent;
import com.enigma.waratsea.event.matcher.BaseCombatMatcher;
import com.enigma.waratsea.event.matcher.BaseMatcher;
import com.enigma.waratsea.event.matcher.EnemyMatcher;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.victory.ShipBombardmentVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.CombatAction.AIRFIELD_ATTACKED;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipBombardmentVictoryTest {
  private ShipBombardmentVictory victoryCondition;

  private static final int POINTS_AWARDED = 5;

  @BeforeEach
  void setUp() {

    var baseMatcher = BaseMatcher.builder()
        .side(ALLIES)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of(BATTLESHIP.name()))
        .build();

    var victoryMatcher = BaseCombatMatcher.builder()
        .base(baseMatcher)
        .actions(Set.of(AIRFIELD_ATTACKED))
        .enemy(enemyMatcher)
        .build();

    victoryCondition = ShipBombardmentVictory.builder()
        .id("bombardment")
        .description("description")
        .points(POINTS_AWARDED)
        .matcher(victoryMatcher)
        .build();
  }


  @Test
  void shouldIncreaseVictoryPoints() {
    var airfieldId = new Id(ALLIES, "airfield");

    var airfield = Airfield.builder()
        .id(airfieldId)
        .build();

    var enemyId = new Id(AXIS, "battleship");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleAirfieldEvent(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(prePoints + POINTS_AWARDED, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var id = new Id(AXIS, "airfield");

    var airfield = Airfield.builder()
        .id(id)
        .build();

    var enemyId = new Id(AXIS, "battleship");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleAirfieldEvent(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
