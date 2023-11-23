package com.enigma.waratsea.viewmodel.pregame.orchestration;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.StartPreferencesEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PreferencesSaga {
  private final Events events;

  public void start() {
    events.getStartPreferencesEvent()
        .fire(new StartPreferencesEvent());
  }
}
