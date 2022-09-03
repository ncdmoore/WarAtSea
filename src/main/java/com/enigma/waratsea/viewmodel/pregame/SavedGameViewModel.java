package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.LoadGameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;

@Slf4j
@Singleton
public class SavedGameViewModel {
  @Getter
  private final ListProperty<Game> savedGamesProperty = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ObjectProperty<Game> selectedSavedGame = new SimpleObjectProperty<>();

  private final Events events;
  private final GameService gameService;

  @Inject
  public SavedGameViewModel(final Events events,
                            final GameService gameService) {
    this.events = events;
    this.gameService = gameService;

    registerEvents();
    loadGames();
  }

  public void goBack(final Stage stage) {
    events.getNavigateEvents().fire(buildBackwardNav(stage));
  }

  public void continueOn(final Stage stage) {
    log.info("continue");
  }

  private void registerEvents() {
    events.getLoadGameEvents().register(this::handleLoadGames);
  }

  private void loadGames() {
    var games = gameService.get();
    savedGamesProperty.setValue(FXCollections.observableList(games));
  }

  private void handleLoadGames(final LoadGameEvent loadGameEvent) {
    loadGames();
  }

  private NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(SavedGameView.class)
        .stage(stage)
        .type(BACKWARD)
        .build();
  }
}
