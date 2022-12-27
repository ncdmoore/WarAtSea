package com.enigma.waratsea.model.squadron;

public enum SquadronState {
  READY,
  QUEUED_FOR_PATROL,
  QUEUED_FOR_MISSION,
  ON_PATROL,
  ON_MISSION,
  RETURNING,
  HANGER,
  DESTROYED
}
