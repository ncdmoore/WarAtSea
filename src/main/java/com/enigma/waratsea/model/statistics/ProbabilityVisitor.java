package com.enigma.waratsea.model.statistics;

import com.enigma.waratsea.model.aircraft.Attack;

public interface ProbabilityVisitor {
  int visit(Attack attack);
}
