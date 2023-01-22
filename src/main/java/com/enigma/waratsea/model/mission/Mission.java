package com.enigma.waratsea.model.mission;

import com.enigma.waratsea.model.taskForce.TaskForce;

import java.util.Set;

public interface Mission {
  String getDescription();
  Set<TaskForce> getTaskForces();
}
