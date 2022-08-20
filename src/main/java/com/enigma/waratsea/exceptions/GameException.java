package com.enigma.waratsea.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Indicates an invalid game
 */
@Slf4j
public class GameException extends RuntimeException {
    public GameException(final String game) {
        super(game);
        log.error("Unable to find game: '{}'", game);
    }
}
