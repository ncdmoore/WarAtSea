package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;

@Slf4j
@Singleton
public class SavedGameViewModel {
  private final Events events;

  @Inject
  public SavedGameViewModel(final Events events) {
    this.events = events;
  }

  public void goBack(final Stage stage) {
    events.getNavigateEvents().fire(buildBackwardNav(stage));
  }

  public void continueOn(final Stage stage) {
    log.info("continue");
  }

  public NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(SavedGameView.class)
        .stage(stage)
        .type(BACKWARD)
        .build();
  }
}
