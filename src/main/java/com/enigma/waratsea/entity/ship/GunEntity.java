package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.ship.ArmourType;
import lombok.Builder;
import lombok.Data;

import static com.enigma.waratsea.model.ship.ArmourType.NONE;

@Data
@Builder
public class GunEntity {
  @Builder.Default
  private ArmourType armour = NONE;

  private int maxHealth;
  private int health;
}
