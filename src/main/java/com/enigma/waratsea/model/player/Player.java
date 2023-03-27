package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.SubmarineFlotilla;
import com.enigma.waratsea.model.airbase.AirbaseType;
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

  Set<Airfield> getAirfields();

  void setAirfields(Set<Airfield> airfields);

  void setPorts(Set<Port> ports);

  Set<TaskForce> getTaskForces();

  void setTaskForces(Set<TaskForce> taskForces);

  Set<SubmarineFlotilla> getSubmarineFlotillas();

  void setSubmarineFlotillas(Set<SubmarineFlotilla> flotillas);

  Set<MtbFlotilla> getMtbFlotillas();

  void setMtbFlotillas(Set<MtbFlotilla> flotillas);

  Set<Squadron> getSquadrons();

  Set<Squadron> getSquadrons(Nation nation);

  Set<Squadron> getSquadrons(AirbaseType airbaseType);

  Set<Squadron> getSquadrons(Nation nation, AirbaseType airbaseType);

  void setSquadrons(Set<Squadron> squadrons);
}
