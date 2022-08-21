package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Game {
    private final GameName gameName;
    private final GameEvents gameEvents = new GameEvents();

    @Setter
    private Scenario scenario;

    public Game(final GameName gameName) {
        this.gameName = gameName;
    }
}
