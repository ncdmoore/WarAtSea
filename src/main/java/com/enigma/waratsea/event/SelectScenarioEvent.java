package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Scenario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelectScenarioEvent implements Event {
  private final Scenario scenario;
}
