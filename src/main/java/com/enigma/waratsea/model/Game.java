package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Game {
    private final GameName gameName;

    @Setter
    private Scenario scenario;

    public Game(final GameName gameName) {
        this.gameName = gameName;
    }
}
