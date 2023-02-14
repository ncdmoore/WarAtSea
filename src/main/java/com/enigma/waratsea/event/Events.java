package com.enigma.waratsea.event;

import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.event.user.*;
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
  private final EventDispatcher<LoadScenarioOptionsEvent> loadScenarioOptionsEvent = new EventDispatcher<>(LoadScenarioOptionsEvent.class);
  private final EventDispatcher<ScenarioHasOptionsEvent> scenarioOptionsEvent = new EventDispatcher<>(ScenarioHasOptionsEvent.class);
  private final EventDispatcher<SelectSideEvent> selectSideEvent = new EventDispatcher<>(SelectSideEvent.class);
  private final EventDispatcher<ConfigNewGameEvent> configNewGameEvent = new EventDispatcher<>(ConfigNewGameEvent.class);
  private final EventDispatcher<ConfigSavedGameEvent> configSavedGameEvent = new EventDispatcher<>(ConfigSavedGameEvent.class);
  private final EventDispatcher<ConfigScenarioOptionsEvent> configScenarioOptionsEvent = new EventDispatcher<>(ConfigScenarioOptionsEvent.class);
  private final EventDispatcher<LoadMapEvent> loadMapEvent = new EventDispatcher<>(LoadMapEvent.class);
  private final EventDispatcher<ClearEvent> clearEvent = new EventDispatcher<>(ClearEvent.class);
  private final EventDispatcher<ApplyAllotmentModEvent> applyAllotmentModEvent = new EventDispatcher<>(ApplyAllotmentModEvent.class);
  private final EventDispatcher<AllotSquadronEvent> allotSquadronEvent = new EventDispatcher<>(AllotSquadronEvent.class);
  private final EventDispatcher<DeploySquadronEvent> deploySquadronEvent = new EventDispatcher<>(DeploySquadronEvent.class);
  private final EventDispatcher<LoadTaskForcesEvent> loadTaskForcesEvent = new EventDispatcher<>(LoadTaskForcesEvent.class);
  private final EventDispatcher<LoadSquadronsEvent> loadSquadronsEvent = new EventDispatcher<>(LoadSquadronsEvent.class);
  private final EventDispatcher<LoadMissionsEvent> loadMissionsEvent = new EventDispatcher<>(LoadMissionsEvent.class);
  private final EventDispatcher<LoadCargoEvent> loadCargoEvent = new EventDispatcher<>(LoadCargoEvent.class);
  private final EventDispatcher<CreatePlayerEvent> createPlayerEvent = new EventDispatcher<>(CreatePlayerEvent.class);
  private final EventDispatcher<SelectSavedGameEvent> selectSavedGameEvent = new EventDispatcher<>(SelectSavedGameEvent.class);
  private final EventDispatcher<NavigateEvent> navigateEvent = new EventDispatcher<>(NavigateEvent.class);
  private final EventDispatcher<ErrorEvent> errorEvents = new EventDispatcher<>(ErrorEvent.class);



  private final EventDispatcher<ShipCombatEvent> shipCombatEvent = new EventDispatcher<>(ShipCombatEvent.class);
}
