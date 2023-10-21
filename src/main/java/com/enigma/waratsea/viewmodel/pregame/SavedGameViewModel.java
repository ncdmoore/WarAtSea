package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.SelectSavedGameEvent;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.enigma.waratsea.viewmodel.pregame.orchestration.SavedGameSaga;
import com.google.inject.Inject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;
import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

@Slf4j
public class SavedGameViewModel {
  @Getter
  private final ListProperty<Game> savedGamesProperty = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ObjectProperty<Game> selectedSavedGame = new SimpleObjectProperty<>();

  private final Events events;
  private final GameService gameService;
  private final SavedGameSaga savedGameSaga;

  @Inject
  public SavedGameViewModel(final Events events,
                            final GameService gameService,
                            final SavedGameSaga savedGameSaga) {
    this.events = events;
    this.gameService = gameService;
    this.savedGameSaga = savedGameSaga;

    selectedSavedGame.addListener(((observable, oldValue, newValue) -> setSelectedSavedGame(newValue)));

    loadGames();
  }

  public void goBack(final Stage stage) {
    goToPreviousPage(stage);
  }

  public void continueOn(final Stage stage) {
    var game = selectedSavedGame.get();

    savedGameSaga.finish(game);
    goToNextPage(stage);
  }

  private void loadGames() {
    var games = gameService.get();
    savedGamesProperty.setValue(FXCollections.observableList(games));
  }

  private void setSelectedSavedGame(final Game game) {
    if (game != null) {
      events.getSelectSavedGameEvent()
          .fire(new SelectSavedGameEvent(game));
    }
  }

  private void goToPreviousPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildBackwardNav(stage));
  }

  private void goToNextPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildForwardNav(stage));
  }

  private NavigateEvent buildForwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(SavedGameView.class)
        .stage(stage)
        .type(FORWARD)
        .build();
  }

  private NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(SavedGameView.class)
        .stage(stage)
        .type(BACKWARD)
        .build();
  }
}
