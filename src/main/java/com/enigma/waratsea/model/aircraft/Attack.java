package com.enigma.waratsea.model.aircraft;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Attack {
  private int modifier;
  private int factor;
  private boolean defensive;
  private double finalModifier;
}
