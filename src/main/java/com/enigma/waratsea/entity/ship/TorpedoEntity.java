package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TorpedoEntity {
  private int maxHealth;
  private int health;
  private int maxNumber;
  private int number;
}
