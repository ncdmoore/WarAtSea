package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.BaseAction;
import com.enigma.waratsea.event.matcher.BaseMatcher;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BaseCombatMatcherEntity {
  private BaseMatcher base;
  private Set<BaseAction> actions;
  private EnemyMatcherEntity enemy;
}
