package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.*;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.taskForce.TaskForce;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ComputerPlayer implements Player {
  private final Side side;

  private Set<Airfield> airfields;

  @Setter
  private Set<Port> ports;

  private Set<TaskForce> taskForces;

  @Setter
  private Set<Squadron> squadrons;

  private final Map<Id, Airbase> airbases = new HashMap<>();

  @Override
  public void setAirfields(final Set<Airfield> airfields) {
    this.airfields = airfields;

    airfields.forEach(this::addToAirbase);
  }

  @Override
  public void setTaskForces(final Set<TaskForce> taskForces) {
    this.taskForces = taskForces;

    taskForces.stream()
        .flatMap(taskForce -> taskForce.getAirbases().stream())
        .forEach(this::addToAirbase);
  }

  private void addToAirbase(final Airbase airbase) {
    airbases.putIfAbsent(airbase.getId(), airbase);
  }
}
