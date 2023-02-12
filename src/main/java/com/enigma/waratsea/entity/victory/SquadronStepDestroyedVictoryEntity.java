package com.enigma.waratsea.entity.victory;

import com.enigma.waratsea.entity.matcher.SquadronCombatMatcherEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadronStepDestroyedVictoryEntity extends VictoryEntity {
  private String id;
  private String description;
  private int points;
  private SquadronCombatMatcherEntity matcher;
  private int totalPoints;

  public SquadronStepDestroyedVictoryEntity() {
    type = "SquadronStepDestroyedVictoryEntity";
  }
}
