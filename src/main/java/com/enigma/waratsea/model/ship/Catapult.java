package com.enigma.waratsea.model.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Catapult {
  private int capacity;
  private int maxHealth;
  private int health;
}
