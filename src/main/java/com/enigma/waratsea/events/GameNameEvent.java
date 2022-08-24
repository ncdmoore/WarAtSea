package com.enigma.waratsea.events;

import com.enigma.waratsea.model.GameName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Thrown on start-up when the game is specified.
 */
@Getter
@RequiredArgsConstructor
public class GameNameEvent implements Event {
  private final GameName gameName;
}
