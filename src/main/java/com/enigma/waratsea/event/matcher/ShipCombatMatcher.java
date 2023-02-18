package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.event.action.ShipAction;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class ShipCombatMatcher {
  private ShipMatcher ship;

  @Builder.Default
  private Set<ShipAction> actions = Collections.emptySet();

  private EnemyMatcher enemy;

  public boolean match(final ShipCombatEvent event) {
    var candidateShip = event.getShip();
    var candidateAction = event.getAction();
    var candidateEnemy = event.getEnemy();

    return matchShip(candidateShip)
        && matchAction(candidateAction)
        && matchEnemy(candidateEnemy);
  }

  private boolean matchShip(final Ship candidateShip) {
    return ship == null || ship.match(candidateShip);
  }

  private boolean matchAction(final ShipAction candidateAction) {
    return actions.isEmpty() || actions.contains(candidateAction);
  }

  private boolean matchEnemy(final Enemy candidateEnemy) {
    return enemy == null || enemy.match(candidateEnemy);
  }
}
