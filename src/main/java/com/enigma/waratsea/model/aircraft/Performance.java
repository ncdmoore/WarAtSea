package com.enigma.waratsea.model.aircraft;

import lombok.Builder;
import lombok.Data;

@Data
public class Performance {
  private final int range;
  private final int endurance;
  private final int ferryDistance;
  private final int radius;

  @Builder
  public Performance(final int range,
                     final int endurance) {
    this.range = range;
    this.endurance = endurance;
    this.ferryDistance = calculateFerryDistance();
    this.radius = calculateRadius();
  }

  private int calculateFerryDistance() {
    return range * endurance;  // One way distance. No return.
  }

  private int calculateRadius() {
    return  (range * endurance) / 2 + ((range % 2) * endurance) / 2;  // Two-way distance. Return.
  }
}
