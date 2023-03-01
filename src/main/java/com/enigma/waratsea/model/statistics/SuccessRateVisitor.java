package com.enigma.waratsea.model.statistics;

import com.enigma.waratsea.model.aircraft.Attack;
import lombok.Builder;

@Builder
public class SuccessRateVisitor implements ProbabilityVisitor {
  public int visit(final Attack attack) {
    return 1;
  }
}
