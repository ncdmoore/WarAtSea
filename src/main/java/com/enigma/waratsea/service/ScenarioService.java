package com.enigma.waratsea.service;

import com.enigma.waratsea.exceptions.ScenarioException;
import com.enigma.waratsea.mapper.ScenarioMapper;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides Scenarios
 */
@Slf4j
@Singleton
public class ScenarioService {
  private final ScenarioRepository scenarioRepository;

  private List<Scenario> scenarios;

  @Inject
  ScenarioService(final ScenarioRepository scenarioRepository) {
    this.scenarioRepository = scenarioRepository;
  }

  public List<Scenario> get() {
    scenarios = getCachedScenarios()
        .orElseGet(this::scenariosFromDisk);

    return scenarios;
  }

  public Scenario get(final int id) {
    return get()
        .stream()
        .filter(scenario -> scenario.getId() == id)
        .findAny()
        .orElseThrow(() -> new ScenarioException("Unable to find scenario with id = " + id));
  }

  private Optional<List<Scenario>> getCachedScenarios() {
    return Optional.ofNullable(scenarios);
  }

  private List<Scenario> scenariosFromDisk() {
    return scenarioRepository
        .get()
        .stream()
        .map(ScenarioMapper.INSTANCE::toModel)
        .sorted()
        .collect(Collectors.toList());
  }
}
