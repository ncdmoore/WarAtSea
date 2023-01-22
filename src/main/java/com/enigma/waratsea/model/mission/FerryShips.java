package com.enigma.waratsea.model.mission;

import com.enigma.waratsea.model.taskForce.TaskForce;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class FerryShips implements Mission {
  private String id;
  private int priority;
  private String description;
  private Set<TaskForce> taskForces;
}
