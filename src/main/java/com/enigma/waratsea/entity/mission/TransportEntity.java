package com.enigma.waratsea.entity.mission;

import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TransportEntity extends MissionEntity {
  private String id;
  private int priority;
  private String description;
  private Set<Id> taskForces;

  public TransportEntity() {
    type = "TransportEntity";
  }
}
