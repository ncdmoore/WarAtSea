package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.SaveGameEvent;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.OrderOfBattleSummaryView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;

@Slf4j
public class OrderOfBattleSummaryViewModel {
  private final Events events;
  private final GameService gameService;

  @Inject
  public OrderOfBattleSummaryViewModel(final Events events,
                                       final GameService gameService) {
    this.events = events;
    this.gameService = gameService;
  }

  public void goBack(final Stage stage) {
    events.getNavigateEvent().fire(buildBackwardNav(stage));
  }

  public void continueOn(final Stage stage) {
    log.info("continue");

    var selectedScenarioName = gameService.getGame()
        .getScenario()
        .getName();

    events.getSaveGameEvent().fire(new SaveGameEvent(selectedScenarioName));
  }

  private NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(OrderOfBattleSummaryView.class)
        .type(BACKWARD)
        .stage(stage)
        .build();
  }
}
