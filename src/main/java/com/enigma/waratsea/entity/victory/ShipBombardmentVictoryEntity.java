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
  private BaseCombatMatcherEntity matcher;

  private int totalPoints;

  public ShipBombardmentVictoryEntity() {
    type = "ShipBombardmentVictoryEntity";
  }
}
