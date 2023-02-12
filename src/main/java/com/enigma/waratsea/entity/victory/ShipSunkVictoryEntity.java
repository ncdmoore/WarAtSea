package com.enigma.waratsea.entity.victory;

import com.enigma.waratsea.entity.matcher.ShipCombatMatcherEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipSunkVictoryEntity extends VictoryEntity {
  private String id;
  private String description;
  private ShipCombatMatcherEntity matcher;
  private int totalPoints;

  public ShipSunkVictoryEntity() {
    type = "ShipSunkVictoryEntity";
  }
}
