package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovementAction {
  ARRIVE("Arrival"),
  LEAVE("Leave");

  private final String value;
}
