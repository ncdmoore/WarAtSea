package com.enigma.watatsea.event.matcher;

import com.enigma.waratsea.event.matcher.EnemyMatcher;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.Id;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.Constants.ANY_SHIP;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyMatcherTest {

  @Test
  void shouldMatchAnyShip() {
    var id = new Id(ALLIES, "Warspite");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(id)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of(ANY_SHIP))
        .side(ALLIES)
        .build();

    var result = enemyMatcher.match(enemy);

    assertTrue(result);
  }

  @Test
  void shouldNotMatchAnyShipWrongSide() {
    var id = new Id(ALLIES, "Warspite");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(id)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of(ANY_SHIP))
        .side(AXIS)
        .build();

    var result = enemyMatcher.match(enemy);

    assertFalse(result);
  }

  @Test
  void shouldNotMatchAnyShipWrongType() {
    var id = new Id(ALLIES, "Blenheim");

    var enemy = Enemy.builder()
        .type("SQUADRON")
        .id(id)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of(ANY_SHIP))
        .side(ALLIES)
        .build();

    var result = enemyMatcher.match(enemy);

    assertFalse(result);
  }

  @Test
  void shouldMatchNameSpecified() {
    var id = new Id(ALLIES, "Warspite");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(id)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of("BATTLESHIP"))
        .names(Set.of("Warspite"))
        .side(ALLIES)
        .build();

    var result = enemyMatcher.match(enemy);

    assertTrue(result);
  }

  @Test
  void shouldNotMatchWrongName() {
    var id = new Id(ALLIES, "Queen Elizabeth");

    var enemy = Enemy.builder()
        .type("BATTLESHIP")
        .id(id)
        .build();

    var enemyMatcher = EnemyMatcher.builder()
        .types(Set.of("BATTLESHIP"))
        .names(Set.of("Warspite"))
        .side(ALLIES)
        .build();

    var result = enemyMatcher.match(enemy);

    assertFalse(result);
  }
}
