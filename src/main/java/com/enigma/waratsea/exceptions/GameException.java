package com.enigma.waratsea.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameException extends WarAtSeaException {
  public GameException(final String game) {
    super(game);
    log.error("Unable to find game: '{}'", game);
  }
}
