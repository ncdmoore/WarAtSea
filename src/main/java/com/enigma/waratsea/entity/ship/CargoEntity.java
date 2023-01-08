package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CargoEntity {
  private int capacity;
  private int level;
  private String originPort;
}
