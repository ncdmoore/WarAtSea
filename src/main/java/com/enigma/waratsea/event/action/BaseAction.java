package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseAction {
  REPAIR("Repair");

  private final String value;
}
