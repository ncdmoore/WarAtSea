package com.enigma.waratsea.model;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class Events {
  private final EventDispatcher<GameNameEvent> gameNameEvents = new EventDispatcher<>("GameNameEvent");
  private final EventDispatcher<NewGameEvent> newGameEvents = new EventDispatcher<>("NewGameEvent");
  private final EventDispatcher<LoadGameEvent> loadGameEvents = new EventDispatcher<>("LoadGameEvent");
  private final EventDispatcher<SaveGameEvent> saveGameEvents = new EventDispatcher<>("SaveGameEvent");
  private final EventDispatcher<SelectScenarioEvent> scenarioEvents = new EventDispatcher<>("ScenarioEvent");
  private final EventDispatcher<SelectSideEvent> sideEvents = new EventDispatcher<>("SideEvent");
  private final EventDispatcher<NavigateEvent> navigateEvents = new EventDispatcher<>("NavigateEvent");
  private final EventDispatcher<ErrorEvent> ErrorEvents = new EventDispatcher<>("ErrorEvent");
}
