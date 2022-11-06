package com.enigma.waratsea.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceEntity {
  private int range;
  private int endurance;
}
