package com.enigma.waratsea.entity.victory;

import com.enigma.waratsea.entity.matcher.BaseCombatMatcherEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipBombardmentVictoryEntity extends VictoryEntity {
  private String id;
  private String description;
  private int points;
  private int requiredOccurrences;
  private BaseCombatMatcherEntity matcher;

  private int totalPoints;
  private int totalOccurrences;

  public ShipBombardmentVictoryEntity() {
    type = "ShipBombardmentVictoryEntity";
  }
}
