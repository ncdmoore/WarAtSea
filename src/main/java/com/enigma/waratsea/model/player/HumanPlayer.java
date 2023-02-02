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
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class HumanPlayer implements Player {
  private final Side side;

  @Setter
  private Set<Nation> nations;

  private Set<Airfield> airfields;

  @Setter
  private Set<Port> ports;

  private Set<TaskForce> taskForces;

  @Setter
  private Set<Squadron> squadrons;

  private final Map<Id, Airbase> airbases = new HashMap<>();

  @Override
  public Set<Squadron> getSquadrons(final Nation nation) {
    return squadrons.stream()
        .filter(squadron -> squadron.ofNation(nation))
        .collect(Collectors.toSet());
  }

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
