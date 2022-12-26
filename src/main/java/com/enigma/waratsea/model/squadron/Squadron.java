package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import lombok.Builder;

@Builder
public class Squadron {
  private Id id;
  private Aircraft aircraft;
}
