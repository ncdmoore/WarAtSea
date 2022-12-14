package com.enigma.waratsea.event;

import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class Events {
  private final EventDispatcher<ConfigApplicationEvent> configApplicationEvent = new EventDispatcher<>(ConfigApplicationEvent.class);
  private final EventDispatcher<GameNameEvent> gameNameEvent = new EventDispatcher<>(GameNameEvent.class);
  private final EventDispatcher<LoadRegistryEvent> loadRegistryEvent = new EventDispatcher<>(LoadRegistryEvent.class);
  private final EventDispatcher<StartNewGameEvent> startNewGameEvent = new EventDispatcher<>(StartNewGameEvent.class);
  private final EventDispatcher<StartSavedGameEvent> startSavedGameEvent = new EventDispatcher<>(StartSavedGameEvent.class);
  private final EventDispatcher<SaveGameEvent> saveGameEvent = new EventDispatcher<>(SaveGameEvent.class);
  private final EventDispatcher<SelectScenarioEvent> selectScenarioEvent = new EventDispatcher<>(SelectScenarioEvent.class);
  private final EventDispatcher<SelectSideEvent> selectSideEvent = new EventDispatcher<>(SelectSideEvent.class);
  private final EventDispatcher<ConfigNewGameEvent> configNewGameEvent = new EventDispatcher<>(ConfigNewGameEvent.class);
  private final EventDispatcher<LoadMapEvent> loadMapEvent = new EventDispatcher<>(LoadMapEvent.class);
  private final EventDispatcher<DeploySquadronEvent> deploySquadronEvent = new EventDispatcher<>(DeploySquadronEvent.class);
  private final EventDispatcher<LoadTaskForcesEvent> loadTaskForcesEvent = new EventDispatcher<>(LoadTaskForcesEvent.class);
  private final EventDispatcher<CreatePlayerEvent> createPlayerEvent = new EventDispatcher<>(CreatePlayerEvent.class);
  private final EventDispatcher<SelectSavedGameEvent> selectSavedGameEvent = new EventDispatcher<>(SelectSavedGameEvent.class);
  private final EventDispatcher<NavigateEvent> navigateEvent = new EventDispatcher<>(NavigateEvent.class);
  private final EventDispatcher<ErrorEvent> ErrorEvents = new EventDispatcher<>(ErrorEvent.class);
}
