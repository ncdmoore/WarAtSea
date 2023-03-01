package com.enigma.waratsea.service;

import com.enigma.waratsea.model.statistics.ProbabilityVisitor;

public interface StatisticsService {
  ProbabilityVisitor getSuccessRate();
}
