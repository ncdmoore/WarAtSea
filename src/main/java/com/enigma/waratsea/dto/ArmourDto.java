package com.enigma.waratsea.dto;

import com.enigma.waratsea.model.ship.ArmourType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArmourDto {
  private ArmourType primary;
  private ArmourType secondary;
  private ArmourType tertiary;
  private ArmourType antiAir;
  private ArmourType hull;
  private boolean deck;
}
