package com.enigma.waratsea.event.user;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.model.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelectSavedGameEvent implements Event {
  private final Game game;
}
