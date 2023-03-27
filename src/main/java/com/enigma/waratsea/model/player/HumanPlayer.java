package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.SubmarineFlotilla;
import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.model.port.Port;
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
  private Set<SubmarineFlotilla> submarineFlotillas;

  @Setter
  private Set<MtbFlotilla> mtbFlotillas;

  @Setter
  private Set<Squadron> squadrons;

  private final Map<Id, Airbase> airbases = new HashMap<>();

  @Override
  public Set<Squadron> getSquadrons(final Nation nation) {
    return squadrons.stream()
        .filter(squadron -> squadron.ofNation(nation))
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Squadron> getSquadrons(final AirbaseType airbaseType) {
    return airbases.values()
        .stream()
        .filter(airbaseType.getFilter())
        .flatMap(airbase -> airbase.getSquadrons().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Squadron> getSquadrons(final Nation nation, final AirbaseType airbaseType) {
    return airbases.values()
        .stream()
        .filter(airbaseType.getFilter())
        .flatMap(airbase -> airbase.getSquadrons().stream())
        .filter(squadron -> squadron.ofNation(nation))
        .collect(Collectors.toSet());
  }

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
