package com.enigma.waratsea.entity.victory;

import com.enigma.waratsea.entity.matcher.ShipFuelMatcherEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipOutOfFuelVictoryEntity extends VictoryEntity {
  private String id;
  private String description;
  private ShipFuelMatcherEntity matcher;
  private int totalPoints;

  public ShipOutOfFuelVictoryEntity() {
    type = "ShipOutOfFuelVictoryEntity";
  }
}
