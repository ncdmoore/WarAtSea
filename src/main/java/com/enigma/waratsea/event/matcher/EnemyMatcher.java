package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.model.Enemy;
import com.enigma.waratsea.model.Side;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class EnemyMatcher {
  @Builder.Default
  private Set<String> types = Collections.emptySet();

  @Builder.Default
  private Set<String> names = Collections.emptySet();

  private Side side;

  public boolean match(final Enemy enemy) {
    return matchType(enemy)
        && matchName(enemy)
        && matchSide(enemy);
  }

  private boolean matchType(final Enemy enemy) {
    var candidateType = enemy.getType();
    return types.isEmpty() || types.contains(candidateType);
  }

  private boolean matchName(final Enemy enemy) {
    var candidateName = enemy.getId().getName();
    return names.isEmpty() || names.contains(candidateName);
  }

  private boolean matchSide(final Enemy enemy) {
    var candidateSide = enemy.getId().getSide();
    return side == null || side == candidateSide;
  }
}
