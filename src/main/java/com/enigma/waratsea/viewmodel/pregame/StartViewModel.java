package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.LoadGameEvent;
import com.enigma.waratsea.event.NewGameEvent;
import com.enigma.waratsea.exceptions.WarAtSeaException;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

@Slf4j
@Singleton
public class StartViewModel {
  private final Events events;

  @Inject
  StartViewModel(final Events events) {
    this.events = events;
  }

  public void newGame(final Stage stage) {
    try {
      events.getNewGameEvents().fire(new NewGameEvent());
      events.getNavigateEvents().fire(buildForwardNavigateEvent(stage));
    } catch (WarAtSeaException e) {
      events.getErrorEvents().fire(buildErrorEvent("Cannot create new game"));
    }
  }

  public void savedGame(final Stage stage) {
    events.getLoadGameEvents().fire(new LoadGameEvent());
    events.getNavigateEvents().fire(buildForwardNavigateEvent(stage));
  }

  public void options() {
    log.info("options todo");
  }

  public void quitGame(final Stage stage) {
    stage.close();
  }

  private NavigateEvent buildForwardNavigateEvent(final Stage stage) {
    return NavigateEvent
        .builder()
        .clazz(StartView.class)
        .stage(stage)
        .type(FORWARD)
        .build();
  }

  public ErrorEvent buildErrorEvent(final String message) {
    return ErrorEvent
        .builder()
        .message(message)
        .fatal(true)
        .build();
  }
}
