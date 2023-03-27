package com.enigma.waratsea.model.squadron;

import java.util.stream.Stream;

public enum DeploymentState {
  NOT_DEPLOYED,
  AT_AIRFIELD,
  ON_SHIP;

  public static Stream<DeploymentState> stream() {
    return Stream.of(DeploymentState.values());
  }
}
