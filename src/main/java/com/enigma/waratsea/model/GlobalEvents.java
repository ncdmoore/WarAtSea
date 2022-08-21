package com.enigma.waratsea.model;

import com.enigma.waratsea.events.EventDispatcher;
import com.enigma.waratsea.events.GameNameEvent;
import com.enigma.waratsea.events.NewGameEvent;
import com.enigma.waratsea.events.ScenarioEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class GlobalEvents {
    private final EventDispatcher<GameNameEvent> gameNameEvents = new EventDispatcher<>("GameNameEvent");
    private final EventDispatcher<NewGameEvent> newGameEvents = new EventDispatcher<>("NewGameEvent");
    private final EventDispatcher<ScenarioEvent> scenarioEvents = new EventDispatcher<>("ScenarioEvent");
}
