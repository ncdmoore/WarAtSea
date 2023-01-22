package com.enigma.waratsea.entity.mission;

import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BombardmentEntity extends MissionEntity {
  private String id;
  private int priority;
  private String description;
  private Set<Id> taskForces;

  public BombardmentEntity() {
    type = "BombardmentEntity";
  }
}
