package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.CombatAction;
import com.enigma.waratsea.event.matcher.BaseMatcher;
import com.enigma.waratsea.event.matcher.EnemyMatcher;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BaseCombatMatcherEntity {
  private BaseMatcher base;
  private Set<CombatAction> actions;
  private EnemyMatcher enemy;
}
