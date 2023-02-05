package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssetState implements Type {
  ACTIVE("Active"),
  RESERVE("Reserve");

  private final String value;
}
