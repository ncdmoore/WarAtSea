package com.enigma.waratsea.model.taskForce;

import com.enigma.waratsea.model.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskForceState implements Type {
  ACTIVE("Active"),
  RESERVE("Reserve");

  private final String value;
}
