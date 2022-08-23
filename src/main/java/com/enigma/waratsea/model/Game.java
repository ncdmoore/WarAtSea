package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a game.
 */
@Getter
public class Game {
  private final GameName gameName;
  private final GameEvents gameEvents = new GameEvents();

  @Setter
  private Scenario scenario;

  @Setter
  private Turn turn;

  public Game(final GameName gameName) {
    this.gameName = gameName;
  }
}
