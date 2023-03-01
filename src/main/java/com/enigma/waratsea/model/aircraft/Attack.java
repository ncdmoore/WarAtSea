package com.enigma.waratsea.model.aircraft;

import com.enigma.waratsea.model.statistics.ProbabilityVisitor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Attack {
  private int modifier;
  private int factor;
  private boolean defensive;
  private double finalModifier;

  public int accept(final ProbabilityVisitor probability) {
    return probability.visit(this);
  }
}
