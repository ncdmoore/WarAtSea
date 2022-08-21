package com.enigma.waratsea.service;

import com.enigma.waratsea.events.GameEvent;
import com.enigma.waratsea.events.ScenarioEvent;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.GlobalEvents;
import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GameService {
    private GameName gameName;

    @Getter
    private Game game;

    @Inject
    public GameService(final GlobalEvents globalEvents) {
        globalEvents.getGameEvents().register(this::setGameName);
        globalEvents.getScenarioEvents().register(this::setScenario);
    }

    public void create() {
        game = new Game(gameName);
    }

    public void setGameName(final GameEvent gameEvent) {
        gameName = gameEvent.getGameName();
        log.debug("Game Service received gameEvent, game set to: '{}'", gameEvent.getGameName());
    }

    private void setScenario(final ScenarioEvent scenarioEvent) {
        game.setScenario(scenarioEvent.getScenario());
        log.debug("Game Service received scenarioEvent, scenario set to: '{}'", scenarioEvent.getScenario().getTitle());
    }
}
