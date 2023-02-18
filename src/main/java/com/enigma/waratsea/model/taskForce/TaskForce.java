package com.enigma.waratsea.model.taskForce;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.AssetState;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.enigma.waratsea.model.AssetState.RESERVE;

@Getter
@Builder
public class TaskForce implements Comparable<TaskForce> {
  private Id id;
  private String title;
  private String location;
  private AssetState state;

  @Builder.Default
  private Set<Mission> missions = new HashSet<>();
  private Set<Ship> ships;

  public Set<Airbase> getAirbases() {
    return ships.stream()
        .filter(this::isAirbase)
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

  public void addMission(final Mission mission) {
    missions.add(mission);
  }

  public boolean isReserved() {
    return state == RESERVE;
  }

  @Override
  public String toString() {
    return id.getName() + " " + title;
  }

  @Override
  public int compareTo(@NotNull final TaskForce o) {
    return id.compareTo(o.id);
  }

  private boolean isAirbase(final Ship ship) {
    return ship instanceof Airbase;
  }
}
