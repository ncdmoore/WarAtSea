package com.enigma.waratsea.model.taskForce;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
public class TaskForce implements Comparable<TaskForce> {
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

  public Map<ShipType, Integer> getShipSummary() {
    return ships.stream()
        .collect(Collectors.groupingBy(Ship::getType,
            Collectors.summingInt(ship -> 1)));
  }

  public Map<AircraftType, Integer> getSquadronSummary() {
     return getAirbases()
        .stream()
        .flatMap(airbase -> airbase.getSquadrons().stream())
         .collect(Collectors.groupingBy(squadron -> squadron.getAircraft().getType(),
             Collectors.summingInt(s -> 1)));
  }

  @Override
  public String toString() {
    return id + " " + title;
  }

  @Override
  public int compareTo(@NotNull TaskForce o) {
    return id.compareTo(o.id);
  }
}
