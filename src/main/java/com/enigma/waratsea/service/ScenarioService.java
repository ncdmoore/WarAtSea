package com.enigma.waratsea.service;

import com.enigma.waratsea.mappers.ScenarioMapper;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class ScenarioService {
  private final ScenarioRepository scenarioRepository;

  @Inject
  public ScenarioService(final ScenarioRepository scenarioRepository) {
    this.scenarioRepository = scenarioRepository;
  }

  public List<Scenario> get() {
    return scenarioRepository
        .get()
        .stream()
        .map(ScenarioMapper.INSTANCE::toModel)
        .sorted()
        .collect(Collectors.toList());
  }
}
