package com.enigma.waratsea.model.taskForce;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
public class TaskForce {
  private String id;
  private String title;
  private String location;
  private TaskForceState state;
  private Set<Ship> ships;

  public Set<Airbase> getAirbases() {
    return ships.stream()
        .filter(Ship::isAirbase)
        .map(ship -> (Airbase) ship)
        .collect(Collectors.toSet());
  }
}
