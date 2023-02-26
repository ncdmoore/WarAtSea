package com.enigma.waratsea.model.squadron;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SquadronState {
  CREATED("Created"),
  READY("Ready"),
  QUEUED_FOR_PATROL("Queued for patrol"),
  QUEUED_FOR_MISSION("Queued for mission"),
  ON_PATROL("On patrol"),
  ON_MISSION("On mission"),
  RETURNING("Returning"),
  HANGER("In hanger"),
  DESTROYED("Destroyed");

  private final String value;
}
