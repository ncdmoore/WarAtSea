package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Singleton
public class GameService {
    private final ResourceNames resourceNames;

    @Setter
    private GameName gameName;

    @Getter
    private Game game;

    @Inject
    public GameService(final ResourceNames resourceNames) {
        this.resourceNames = resourceNames;
    }

    public void initialize(final GameName gameName) {
        setGameName(gameName);
        resourceNames.setGameName(gameName.getValue());
    }

    public void create() {
        game = new Game(gameName);
    }

    public void setScenario(final Scenario scenario) {
        game.setScenario(scenario);
        resourceNames.setScenarioName(scenario.getName());
    }
}
