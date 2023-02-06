package com.enigma.waratsea.model;

import com.enigma.waratsea.model.squadron.SquadronDeploymentType;
import com.enigma.waratsea.model.turn.TimeRange;
import com.enigma.waratsea.model.weather.Weather;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Scenario implements Comparable<Scenario> {
  @EqualsAndHashCode.Include
  private Integer id;
  private String name;
  private String title;
  private String image;
  private String description;
  private LocalDate date;
  private Weather weather;
  private int maxTurns;
  private TimeRange timeRange;
  private String map;
  private String timeFrame;
  private SquadronDeploymentType squadron;
  private boolean minefieldForHumanSide;

  @Override
  public String toString() {
    return title;
  }

  @Override
  public int compareTo(@NotNull Scenario o) {
    return id.compareTo(o.id);
  }
}
