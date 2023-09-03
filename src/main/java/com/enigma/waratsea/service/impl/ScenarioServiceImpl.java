package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.exception.ScenarioException;
import com.enigma.waratsea.mapper.ScenarioMapper;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.enigma.waratsea.service.ScenarioService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ScenarioServiceImpl implements ScenarioService {
  private final ScenarioRepository scenarioRepository;
  private List<Scenario> scenarios;

  @Override
  public List<Scenario> get() {
    scenarios = getCachedScenarios()
        .orElseGet(this::getScenairosFromRepository);

    return scenarios;
  }

  @Override
  public Scenario get(final int id) {
    return get()
        .stream()
        .filter(scenario -> scenario.getId() == id)
        .findAny()
        .orElseThrow(() -> new ScenarioException("Unable to find scenario with id: " + id));
  }

  private Optional<List<Scenario>> getCachedScenarios() {
    return Optional.ofNullable(scenarios);
  }

  private List<Scenario> getScenairosFromRepository() {
    return scenarioRepository
        .get()
        .stream()
        .map(ScenarioMapper.INSTANCE::toModel)
        .sorted()
        .collect(Collectors.toList());
  }
}
