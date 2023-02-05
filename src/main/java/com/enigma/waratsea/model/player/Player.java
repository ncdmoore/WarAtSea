package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.*;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.taskForce.TaskForce;

import java.util.Map;
import java.util.Set;

public interface Player {
  Side getSide();

  Set<Nation> getNations();
  void setNations(Set<Nation> nations);

  Map<Id, Airbase> getAirbases();

  void setAirfields(Set<Airfield> airfields);

  void setPorts(Set<Port> ports);

  Set<TaskForce> getTaskForces();
  void setTaskForces(Set<TaskForce> taskForces);

  Set<SubmarineFlotilla> getSubmarineFlotillas();
  void setSubmarineFlotillas(Set<SubmarineFlotilla> flotillas);

  Set<Squadron> getSquadrons();
  Set<Squadron> getSquadrons(Nation nation);
  void setSquadrons(Set<Squadron> squadrons);
}
