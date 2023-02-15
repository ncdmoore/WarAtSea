package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.Events;
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
  private Events events;
  private ShipBombardmentVictory victoryConditionNoRequiredOccurrences;
  private ShipBombardmentVictory victoryConditionRequiredOccurrences;

  private static final int POINTS_AWARDED = 5;

  @BeforeEach
  void setUp() {
    events = new Events();

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

    victoryConditionNoRequiredOccurrences = ShipBombardmentVictory.builder()
        .id("bombardment")
        .description("description")
        .points(POINTS_AWARDED)
        .matcher(victoryMatcher)
        .build();

    victoryConditionRequiredOccurrences = ShipBombardmentVictory.builder()
        .id("bombardment")
        .description("description")
        .points(POINTS_AWARDED)
        .requiredOccurrences(2)
        .matcher(victoryMatcher)
        .build();

    victoryConditionNoRequiredOccurrences.registerEvents(events);
    victoryConditionRequiredOccurrences.registerEvents(events);
  }

  @Test
  void shouldIncreaseVictoryPoints() {
    var airfieldId = new Id(ALLIES, "airfield");

    var airfield = Airfield.builder()
        .id(airfieldId)
        .build();

    var enemyId = new Id(AXIS, "battleship");

    var enemy = Enemy.builder()
        .type(BATTLESHIP.name())
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryConditionNoRequiredOccurrences.getTotalPoints();

    bombardAirfield(event);

    var postPoints = victoryConditionNoRequiredOccurrences.getTotalPoints();

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
        .type(BATTLESHIP.name())
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryConditionNoRequiredOccurrences.getTotalPoints();

    bombardAirfield(event);

    var postPoints = victoryConditionNoRequiredOccurrences.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }

  @Test
  void shouldIncreaseVictoryPointsRequiredOccurrencesSatisfied() {
    var id = new Id(ALLIES, "airfield");

    var airfield = Airfield.builder()
        .id(id)
        .build();

    var enemyId = new Id(AXIS, "battleship");

    var enemy = Enemy.builder()
        .type(BATTLESHIP.name())
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryConditionRequiredOccurrences.getTotalPoints();

    bombardAirfield(event); // 1st bombardment
    bombardAirfield(event); // 2nd bombardment

    var postPoints = victoryConditionRequiredOccurrences.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(prePoints + POINTS_AWARDED, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPointsRequiredOccurrencesNotSatisfied() {
    var id = new Id(ALLIES, "airfield");

    var airfield = Airfield.builder()
        .id(id)
        .build();

    var enemyId = new Id(AXIS, "battleship");

    var enemy = Enemy.builder()
        .type(BATTLESHIP.name())
        .id(enemyId)
        .build();

    var event = new AirfieldCombatEvent(airfield, AIRFIELD_ATTACKED, enemy);

    var prePoints = victoryConditionRequiredOccurrences.getTotalPoints();

    bombardAirfield(event);

    var postPoints = victoryConditionRequiredOccurrences.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }

  private void bombardAirfield(final AirfieldCombatEvent event) {
    events.getAirfieldCombatEvent().fire(event);

  }
}
