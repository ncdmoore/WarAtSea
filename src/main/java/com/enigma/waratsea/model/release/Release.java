package com.enigma.waratsea.model.release;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.taskForce.TaskForce;

import java.util.Set;

public interface Release {
  String getDescription();
  Set<TaskForce> getTaskForces();
  void registerEvents(Events events);
}
