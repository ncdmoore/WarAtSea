package com.enigma.waratsea.events;

import com.enigma.waratsea.model.GameName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Used to broadcast the setting of the game name.
 */
@Getter
@RequiredArgsConstructor
public class GameNameEvent implements Event {
  private final GameName gameName;
}
