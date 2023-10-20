package com.enigma.waratsea.model;

import com.enigma.waratsea.model.turn.TimeRange;
import com.enigma.waratsea.model.weather.Weather;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Set;

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
  private Set<NationId> nationsWithAllotmentOptions;
  private Set<Side> sidesWithAllotments;

  public boolean hasAllotmentOptions(final Side side) {
    var sideWithOptions = nationsWithAllotmentOptions.stream()
        .findAny()
        .map(NationId::getSide)
        .orElse(null);

    return side == sideWithOptions;
  }

  public boolean hasAllotments() {
    return !sidesWithAllotments.isEmpty();
  }

  public boolean hasAllotment(final Side side) {
    return sidesWithAllotments.contains(side);
  }

  @Override
  public String toString() {
    return title;
  }

  @Override
  public int compareTo(@NotNull final Scenario o) {
    return id.compareTo(o.id);
  }
}
