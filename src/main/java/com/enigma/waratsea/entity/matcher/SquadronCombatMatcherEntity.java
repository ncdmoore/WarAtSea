package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.CombatAction;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SquadronCombatMatcherEntity {
  private SquadronMatcherEntity squadron;
  private Set<CombatAction> actions;
}

