package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.StartSavedGameEvent;
import com.enigma.waratsea.event.StartNewGameEvent;
import com.enigma.waratsea.exceptions.WarAtSeaException;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

@Slf4j
public class StartViewModel {
  @Getter
  private final BooleanProperty savedGamesExist = new SimpleBooleanProperty();

  private final Events events;

  @Inject
  StartViewModel(final Events events,
                 final GameService gameService) {
    this.events = events;

    savedGamesExist.setValue(gameService.get().isEmpty());
  }

  public void newGame(final Stage stage) {
    try {
      events.getStartNewGameEvents().fire(new StartNewGameEvent());
      events.getNavigateEvents().fire(buildForwardNavigateEvent(stage));
    } catch (WarAtSeaException e) {
      events.getErrorEvents().fire(buildErrorEvent());
    }
  }

  public void savedGame(final Stage stage) {
    events.getStartSavedGameEvents().fire(new StartSavedGameEvent());
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

  private ErrorEvent buildErrorEvent() {
    return ErrorEvent
        .builder()
        .message("Cannot create new game")
        .fatal(true)
        .build();
  }
}
