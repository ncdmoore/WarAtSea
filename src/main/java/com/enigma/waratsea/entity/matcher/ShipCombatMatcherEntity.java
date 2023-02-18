package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.ShipAction;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShipCombatMatcherEntity {
  private ShipMatcherEntity ship;
  private Set<ShipAction> actions;
  private EnemyMatcherEntity enemy;
}

