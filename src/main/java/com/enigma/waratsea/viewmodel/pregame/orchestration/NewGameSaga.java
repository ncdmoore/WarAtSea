package com.enigma.waratsea.viewmodel.pregame.orchestration;

import com.enigma.waratsea.event.ConfigNewGameEvent;
import com.enigma.waratsea.event.ConfigScenarioOptionsEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.ScenarioHasOptionsEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.Side;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class NewGameSaga {
  private final Events events;

  public void start() {
    events.getStartNewGameEvent()
        .fire(new StartNewGameEvent());
  }

  public void scenarioSelected(final Scenario scenario, final Side side) {
    events.getScenarioOptionsEvent()
        .fire(new ScenarioHasOptionsEvent(scenario, side));

    if (!scenario.hasAllotmentOptions(side)) {
      events.getConfigNewGameEvent()
          .fire(new ConfigNewGameEvent(scenario));
    }
  }

  public void squadronOptionsSelected(final Scenario scenario, final Map<NationId, Integer> options) {
    events.getConfigScenarioOptionsEvent().fire(new ConfigScenarioOptionsEvent(options));
    events.getConfigNewGameEvent().fire(new ConfigNewGameEvent(scenario));
  }

  public void finish(final String selectedScenarioName) {
    events.getSaveGameEvent()
        .fire(new SaveGameEvent(selectedScenarioName));
  }
}
