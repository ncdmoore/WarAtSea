package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConfigSavedGameEvent implements Event {
  private final Game game;
}
