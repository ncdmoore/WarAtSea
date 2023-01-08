package com.enigma.waratsea.model.ship;

import lombok.Data;

@Data
public class Hull {
  private ArmourType armour;
  private int maxHealth;
  private int health;
  private boolean deck;
}
