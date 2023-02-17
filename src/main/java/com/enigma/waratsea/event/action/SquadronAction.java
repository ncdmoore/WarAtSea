package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SquadronAction {
  SQUADRON_ATTACKED("Squadron attacked"),
  SQUADRON_DAMAGED("Squadron damaged"),
  SQUADRON_DESTROYED("Squadron destroyed"),

  SQUADRON_ARRIVED("Squadron arrived"),
  SQUADRON_LEFT("Squadron left");

  private final String value;
}
