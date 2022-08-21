package com.enigma.waratsea.model;

import com.enigma.waratsea.events.EventDispatcher;
import com.enigma.waratsea.events.GameEvent;
import com.enigma.waratsea.events.ScenarioEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Singleton
public class GlobalEvents {
    @Getter
    private final EventDispatcher<ScenarioEvent> scenarioEvents = new EventDispatcher<>("ScenarioEvent");

    @Getter
    private final EventDispatcher<GameEvent> gameEvents = new EventDispatcher<>("GameEvent");
}
