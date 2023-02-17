package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.event.action.BaseAction;
import com.enigma.waratsea.event.airfield.AirfieldCombatEvent;
import com.enigma.waratsea.event.port.PortCombatEvent;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.port.Port;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class BaseCombatMatcher {
  private BaseMatcher base;

  @Builder.Default
  private Set<BaseAction> actions = Collections.emptySet();

  private EnemyMatcher enemy;

  public boolean match(final AirfieldCombatEvent event) {
    var candidateBase = event.getAirfield();
    var candidateAction = event.getAction();
    var candidateEnemy = event.getEnemy();

    return matchAirfield(candidateBase)
        && matchAction(candidateAction)
        &&  matchEnemy(candidateEnemy);
  }

  public boolean match(final PortCombatEvent event) {
    var candidateBase = event.getPort();
    var candidateAction = event.getAction();
    var candidateEnemy = event.getEnemy();

    return matchPort(candidateBase)
        && matchAction(candidateAction)
        && matchEnemy(candidateEnemy);
  }

  private boolean matchAirfield(final Airfield candidateAirfield) {
    return base == null || base.match(candidateAirfield);
  }

  private boolean matchPort(final Port candidatePort) {
    return base == null || base.match(candidatePort);
  }

  private boolean matchAction(final BaseAction candidateAction) {
    return actions.isEmpty() || actions.contains(candidateAction);
  }

  private boolean matchEnemy(final Enemy candidateEnemy) {
    return enemy == null || enemy.match(candidateEnemy);
  }
}
