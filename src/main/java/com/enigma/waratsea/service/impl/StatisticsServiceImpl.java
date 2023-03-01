package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.model.statistics.ProbabilityVisitor;
import com.enigma.waratsea.model.statistics.SuccessRateVisitor;
import com.enigma.waratsea.service.StatisticsService;
import com.google.inject.Singleton;

@Singleton
public class StatisticsServiceImpl implements StatisticsService {
  @Override
  public ProbabilityVisitor getSuccessRate() {
    return SuccessRateVisitor.builder().build();
  }
}
