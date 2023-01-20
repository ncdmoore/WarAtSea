package com.enigma.waratsea.model.aircraft;

import com.enigma.waratsea.model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AircraftType implements Type {
  FIGHTER("Fighter", "Fighter", "F"),
  BOMBER("Bomber", "Bomber", "B"),
  DIVE_BOMBER("Dive Bomber", "Dive Bomber", "DB"),
  TORPEDO_BOMBER("Torpedo Bomber", "Torp. Bomber", "TB"),
  RECONNAISSANCE("Reconnaissance", "Recon", "R");

  private final String value;
  private final String abbreviated;
  private final String designation;
}
