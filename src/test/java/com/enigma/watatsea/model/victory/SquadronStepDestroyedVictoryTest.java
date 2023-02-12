package com.enigma.watatsea.model.victory;

import com.enigma.waratsea.event.matcher.SquadronCombatMatcher;
import com.enigma.waratsea.event.matcher.SquadronMatcher;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.victory.SquadronStepDestroyedVictory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.event.action.CombatAction.*;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.aircraft.AircraftType.BOMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SquadronStepDestroyedVictoryTest {
  private SquadronStepDestroyedVictory victoryCondition;

  private static final int POINTS_AWARDED = 6;

  @BeforeEach
  void setUp() {
    var squadronMatcher = SquadronMatcher.builder()
        .side(ALLIES)
        .build();

    var victoryMatcher = SquadronCombatMatcher.builder()
        .squadron(squadronMatcher)
        .actions(Set.of(SQUADRON_DAMAGED, SQUADRON_DESTROYED))
        .build();

    victoryCondition = SquadronStepDestroyedVictory.builder()
        .id("damaged")
        .description("description")
        .points(POINTS_AWARDED)
        .matcher(victoryMatcher)
        .build();
  }


  @Test
  void shouldIncreaseVictoryPoints() {
    var id = new Id(ALLIES, "bomber");

    var aircraft = Aircraft.builder()
        .type(BOMBER)
        .build();

    var squadron = Squadron.builder()
        .id(id)
        .aircraft(aircraft)
        .build();

    var event = new SquadronCombatEvent(squadron, SQUADRON_DAMAGED);

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleSquadronEvent(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(prePoints + POINTS_AWARDED, postPoints);
  }

  @Test
  void shouldNotIncreaseVictoryPoints() {
    var id = new Id(AXIS, "bomber");

    var aircraft = Aircraft.builder()
        .type(BOMBER)
        .build();

    var squadron = Squadron.builder()
        .id(id)
        .aircraft(aircraft)
        .build();

    var event = new SquadronCombatEvent(squadron, SQUADRON_ATTACKED);

    var prePoints = victoryCondition.getTotalPoints();

    victoryCondition.handleSquadronEvent(event);

    var postPoints = victoryCondition.getTotalPoints();

    assertEquals(0, prePoints);
    assertEquals(0, postPoints);
  }
}
