package com.enigma.waratsea.event;

import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class Events {
  private final EventDispatcher<GameNameEvent> gameNameEvents = new EventDispatcher<>(GameNameEvent.class);
  private final EventDispatcher<StartNewGameEvent> startNewGameEvents = new EventDispatcher<>(StartNewGameEvent.class);
  private final EventDispatcher<StartSavedGameEvent> startSavedGameEvents = new EventDispatcher<>(StartSavedGameEvent.class);
  private final EventDispatcher<SaveGameEvent> saveGameEvents = new EventDispatcher<>(SaveGameEvent.class);
  private final EventDispatcher<SelectScenarioEvent> selectScenarioEvent = new EventDispatcher<>(SelectScenarioEvent.class);
  private final EventDispatcher<SelectSideEvent> selectSideEvent = new EventDispatcher<>(SelectSideEvent.class);
  private final EventDispatcher<ConfigNewGameEvent> configNewGameEvent = new EventDispatcher<>(ConfigNewGameEvent.class);
  private final EventDispatcher<LoadMapEvent> loadMapEvent = new EventDispatcher<>(LoadMapEvent.class);
  private final EventDispatcher<LoadPlayerEvent> loadPlayerEvent = new EventDispatcher<>(LoadPlayerEvent.class);
  private final EventDispatcher<SelectSavedGameEvent> selectSavedGameEvent = new EventDispatcher<>(SelectSavedGameEvent.class);
  private final EventDispatcher<NavigateEvent> navigateEvents = new EventDispatcher<>(NavigateEvent.class);
  private final EventDispatcher<ErrorEvent> ErrorEvents = new EventDispatcher<>(ErrorEvent.class);
}
