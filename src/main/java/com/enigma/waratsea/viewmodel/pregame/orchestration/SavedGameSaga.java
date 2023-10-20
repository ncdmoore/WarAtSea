package com.enigma.waratsea.viewmodel.pregame.orchestration;

import com.enigma.waratsea.event.ConfigSavedGameEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.model.Game;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class SavedGameSaga {
  private final Events events;

  public void start() {
    events.getStartSavedGameEvent().fire(new StartSavedGameEvent());
  }

  public void finish(final Game game) {
    events.getConfigSavedGameEvent().fire(new ConfigSavedGameEvent(game));
  }
}
