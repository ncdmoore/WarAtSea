package com.enigma.waratsea.entity.mission;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BombardmentEntity extends MissionEntity {
  private String id;
  private int priority;
  private String description;
  private Set<String> taskForces;

  public BombardmentEntity() {
    type = "BombardmentEntity";
  }
}
