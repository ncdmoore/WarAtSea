package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CargoAction {
  CARGO_LOADED("Cargo loaded"),
  CARGO_UNLOADED("Cargo unloaded");

  private final String value;
}
