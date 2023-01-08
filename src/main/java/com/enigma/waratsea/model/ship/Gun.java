package com.enigma.waratsea.model.ship;

import lombok.Data;

@Data
public class Gun {
  private ArmourType armour;
  private int maxHealth;
  private int health;
}
