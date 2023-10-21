package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.exception.WarAtSeaException;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.enigma.waratsea.viewmodel.pregame.orchestration.NewGameSaga;
import com.enigma.waratsea.viewmodel.pregame.orchestration.SavedGameSaga;
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

  private final NewGameSaga newGameSaga;
  private final SavedGameSaga savedGameSaga;
  private final Events events;

  @Inject
  StartViewModel(final Events events,
                 final GameService gameService,
                 final NewGameSaga newGameSaga,
                 final SavedGameSaga savedGameSaga) {
    this.newGameSaga = newGameSaga;
    this.savedGameSaga = savedGameSaga;
    this.events = events;

    savedGamesExist.setValue(gameService.get().isEmpty());
  }

  public void newGame(final Stage stage) {
    try {
      newGameSaga.start();
      goToNextPage(stage);
    } catch (WarAtSeaException e) {
      events.getErrorEvents().fire(buildErrorEvent());
    }
  }

  public void savedGame(final Stage stage) {
    savedGameSaga.start();
    goToNextPage(stage);
  }

  public void options() {
    log.info("options todo");
  }

  public void quitGame(final Stage stage) {
    stage.close();
  }

  private void goToNextPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildForwardNavigateEvent(stage));
  }

  private NavigateEvent buildForwardNavigateEvent(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(StartView.class)
        .stage(stage)
        .type(FORWARD)
        .build();
  }

  private ErrorEvent buildErrorEvent() {
    return ErrorEvent.builder()
        .message("Cannot create new game")
        .fatal(true)
        .build();
  }
}
