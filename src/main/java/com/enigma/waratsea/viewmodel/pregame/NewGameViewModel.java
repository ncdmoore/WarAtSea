package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.SaveGameEvent;
import com.enigma.waratsea.event.ScenarioEvent;
import com.enigma.waratsea.event.SideEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.service.ScenarioService;
import com.enigma.waratsea.view.pregame.NewGameView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;

@Slf4j
@Singleton
public class NewGameViewModel {
  @Getter
  private final ListProperty<Scenario> scenariosProperty = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ObjectProperty<Scenario> selectedScenario = new SimpleObjectProperty<>();

  @Getter
  private final ObjectProperty<Toggle> selectedSide = new SimpleObjectProperty<>();

  private final Events events;

  @Inject
  NewGameViewModel(final Events events,
                   final ScenarioService scenarioService) {
    this.events = events;

    var scenarios = scenarioService.get();
    scenariosProperty.setValue(FXCollections.observableList(scenarios));

    selectedScenario.addListener((observable, oldValue, newValue) -> setSelectedScenario(newValue));
    selectedSide.addListener((observable, oldValue, newValue) -> setSelectedSide(newValue));
  }

  public void goBack(final Stage stage) {
    events.getNavigateEvents().fire(buildBackwardNav(stage));
  }

  public void continueOn(final Stage stage) {
    log.info("continue");
    events.getSaveGameEvents().fire(new SaveGameEvent());
  }

  private void setSelectedScenario(final Scenario scenario) {
    if (scenario != null) { // can be null as listview is un-staged?
      events.getScenarioEvents().fire(new ScenarioEvent(scenario));
    }
  }

  private void setSelectedSide(final Toggle toggle) {
    var selectedSide = getSelectedSideFromToggle(toggle);
    events.getSideEvents().fire(new SideEvent(selectedSide));
  }

  private Side getSelectedSideFromToggle(final Toggle toggle) {
    return (Side) toggle.getToggleGroup()
        .getToggles()
        .stream()
        .filter(Toggle::isSelected)
        .findFirst()
        .orElseThrow()
        .getUserData();
  }

  private NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(NewGameView.class)
        .type(BACKWARD)
        .stage(stage)
        .build();
  }
}