package com.enigma.waratsea.events;

import com.enigma.waratsea.model.Scenario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Broadcast the selection of the scenario.
 */
@Getter
@RequiredArgsConstructor
public class ScenarioEvent implements Event {
  private final Scenario scenario;
}
