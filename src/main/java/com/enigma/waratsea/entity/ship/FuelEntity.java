package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelEntity {
  private int capacity;
  private int level;
}
