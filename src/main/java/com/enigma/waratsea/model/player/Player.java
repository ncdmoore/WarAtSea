package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.*;
import com.enigma.waratsea.model.taskForce.TaskForce;

import java.util.Map;
import java.util.Set;

public interface Player {
  Side getSide();
  Map<Id, Airbase> getAirbases();
  void setAirfields(Set<Airfield> airfields);
  void setPorts(Set<Port> ports);
  Set<TaskForce> getTaskForces();
  void setTaskForces(Set<TaskForce> taskForces);
}
