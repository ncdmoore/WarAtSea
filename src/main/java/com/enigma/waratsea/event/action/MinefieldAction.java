package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MinefieldAction {
  MINEFIELD_LAID("Minefield laid"),
  MINEFIELD_CLEARED("Minefield cleared");

  private final String value;
}
