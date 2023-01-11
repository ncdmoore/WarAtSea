package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatapultEntity {
  private int capacity;
  private int maxHealth;
  private int health;
}
