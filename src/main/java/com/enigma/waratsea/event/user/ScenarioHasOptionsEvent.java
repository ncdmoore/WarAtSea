package com.enigma.waratsea.event.user;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.Side;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScenarioHasOptionsEvent implements Event {
  private final Scenario scenario;
  private final Side side;
}
