package com.enigma.waratsea.entity.release;

import com.enigma.waratsea.entity.matcher.ShipCombatMatcherEntity;
import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ShipCombatReleaseEntity extends ReleaseEntity {
  private String id;
  private String description;
  private ShipCombatMatcherEntity matcher;
  private Set<Id> taskForces;

  public ShipCombatReleaseEntity() {
    setType("ShipCombatReleaseEntity");
  }
}
