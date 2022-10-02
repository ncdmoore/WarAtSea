package com.enigma.waratsea.repository.impl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GlobalDirectory {
  GAME_DIRECTORY("game"),
  SCENARIO_DIRECTORY("scenarios");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}
