package com.enigma.waratsea.dto;

import com.enigma.waratsea.model.ship.Gun;
import com.enigma.waratsea.model.ship.Torpedo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeaponsDto {
  private Gun primary;
  private Gun secondary;
  private Gun tertiary;
  private Gun antiAir;
  private Torpedo torpedo;
  private boolean asw;
}
