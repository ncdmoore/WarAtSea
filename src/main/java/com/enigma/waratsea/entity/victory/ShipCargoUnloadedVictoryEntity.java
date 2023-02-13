package com.enigma.waratsea.entity.victory;

import com.enigma.waratsea.entity.matcher.ShipCargoMatcherEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipCargoUnloadedVictoryEntity extends VictoryEntity {
  private String id;
  private String description;
  private int points;
  private int factor;
  private ShipCargoMatcherEntity matcher;

  private int totalPoints;

  public ShipCargoUnloadedVictoryEntity() {
    type = "ShipCargoUnloadedVictoryEntity";
  }

}
