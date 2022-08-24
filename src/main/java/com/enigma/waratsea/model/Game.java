package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a game.
 */
@Getter
public class Game {
  private final GameName gameName;

  @Setter
  private Scenario scenario;

  @Setter
  private Side humanSide;

  @Setter
  private Turn turn;

  public Game(final GameName gameName) {
    this.gameName = gameName;
  }
}
