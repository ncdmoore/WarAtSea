package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Scenario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Thrown when a scenario is selected.
 */
@Getter
@RequiredArgsConstructor
public class ScenarioEvent implements Event {
  private final Scenario scenario;
}
