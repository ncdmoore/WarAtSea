package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.option.AllotmentModification;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.SquadronAllotmentModService;
import com.enigma.waratsea.view.pregame.ScenarioSquadronOptionsView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.enigma.waratsea.viewmodel.events.NavigationType;
import com.enigma.waratsea.viewmodel.pregame.orchestration.NewGameSaga;
import com.google.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;
import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

@Slf4j
public class ScenarioSquadronOptionsViewModel {
  private final Events events;
  private final GameService gameService;
  private final SquadronAllotmentModService squadronAllotmentModService;
  private final NewGameSaga newGameSaga;

  @Getter
  private Map<NationId, Set<AllotmentModification>> options;

  @Getter
  private final Map<NationId, ObjectProperty<Toggle>> selectedOptions = new HashMap<>();

  @Inject
  public ScenarioSquadronOptionsViewModel(final Events events,
                                          final GameService gameService,
                                          final SquadronAllotmentModService squadronAllotmentModService,
                                          final NewGameSaga newGameSaga) {
    this.events = events;
    this.gameService = gameService;
    this.squadronAllotmentModService = squadronAllotmentModService;
    this.newGameSaga = newGameSaga;

    initializeOptions();
  }

  public void goBack(final Stage stage) {
    goToPreviousPage(stage);
  }

  public void continueOn(final Stage stage) {
    var scenario = gameService.getGame().getScenario();
    var currentOptions = getSelectedOptionsFormToggles();

    newGameSaga.squadronOptionsSelected(scenario, currentOptions);
    goToNextPage(stage);
  }

  private void initializeOptions() {
    var scenario = gameService.getGame().getScenario();
    var humanSide = gameService.getGame().getHumanSide();

    options = scenario.getNationsWithAllotmentOptions()
        .stream()
        .filter(id -> id.getSide() == humanSide)
        .collect(Collectors.toMap(id -> id, squadronAllotmentModService::get));

    options.keySet().forEach(nationId -> selectedOptions.put(nationId, new SimpleObjectProperty<>()));
  }

  private void goToPreviousPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildNavigateEvent(BACKWARD, stage));
  }

  private void goToNextPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildNavigateEvent(FORWARD, stage));
  }

  private NavigateEvent buildNavigateEvent(final NavigationType type, final Stage stage) {
    return NavigateEvent.builder()
        .clazz(ScenarioSquadronOptionsView.class)
        .stage(stage)
        .type(type)
        .build();
  }

  private Map<NationId, Integer> getSelectedOptionsFormToggles() {
    return selectedOptions.entrySet()
        .stream()
        .map(this::getSelectedOptionFromToggle)
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private Pair<NationId, Integer> getSelectedOptionFromToggle(final Map.Entry<NationId, ObjectProperty<Toggle>> entry) {
    var nationId = entry.getKey();
    var toggle = entry.getValue().get();

    var optionId = (Integer) toggle.getToggleGroup()
        .getToggles()
        .stream()
        .filter(Toggle::isSelected)
        .findFirst()
        .orElseThrow()
        .getUserData();

    return new Pair<>(nationId, optionId);
  }
}
