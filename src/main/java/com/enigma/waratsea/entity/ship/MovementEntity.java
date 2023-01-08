package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementEntity {
  private int maxEven;
  private int maxOdd;
  private int even;
  private int odd;
}
