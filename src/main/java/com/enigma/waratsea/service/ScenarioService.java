package com.enigma.waratsea.service;

import com.enigma.waratsea.entity.Scenario;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;

    @Inject
    public ScenarioService(final ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public List<Scenario> get() {
        return scenarioRepository.get();
    }
}
