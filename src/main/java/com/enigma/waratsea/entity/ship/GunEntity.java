package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.ship.ArmourType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GunEntity {
  private ArmourType armour;
  private int maxHealth;
  private int health;
}
