package com.enigma.waratsea.event;

import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class Events {
  private final EventDispatcher<GameNameEvent> gameNameEvents = new EventDispatcher<>(GameNameEvent.class);
  private final EventDispatcher<NewGameEvent> newGameEvents = new EventDispatcher<>(NewGameEvent.class);
  private final EventDispatcher<LoadGameEvent> loadGameEvents = new EventDispatcher<>(LoadGameEvent.class);
  private final EventDispatcher<SaveGameEvent> saveGameEvents = new EventDispatcher<>(SaveGameEvent.class);
  private final EventDispatcher<SelectScenarioEvent> selectScenarioEvent = new EventDispatcher<>(SelectScenarioEvent.class);
  private final EventDispatcher<SelectSideEvent> selectSideEvent = new EventDispatcher<>(SelectSideEvent.class);
  private final EventDispatcher<ConfigGameEvent> configGameEvent = new EventDispatcher<>(ConfigGameEvent.class);
  private final EventDispatcher<LoadMapEvent> loadMapEvent = new EventDispatcher<>(LoadGameEvent.class);
  private final EventDispatcher<LoadPlayerEvent> loadPlayerEvent = new EventDispatcher<>(LoadPlayerEvent.class);
  private final EventDispatcher<SelectSavedGameEvent> selectSavedGameEvent = new EventDispatcher<>(SelectSavedGameEvent.class);
  private final EventDispatcher<NavigateEvent> navigateEvents = new EventDispatcher<>(NavigateEvent.class);
  private final EventDispatcher<ErrorEvent> ErrorEvents = new EventDispatcher<>(ErrorEvent.class);
}
