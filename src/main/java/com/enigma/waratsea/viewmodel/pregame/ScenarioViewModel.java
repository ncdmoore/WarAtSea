package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.SaveGameEvent;
import com.enigma.waratsea.event.ScenarioEvent;
import com.enigma.waratsea.event.SideEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.service.ScenarioService;
import com.enigma.waratsea.view.pregame.NewGameView;
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

/**
 * Scenario view model.
 */
@Slf4j
@Singleton
public class ScenarioViewModel {
  @Getter
  private final ListProperty<Scenario> scenariosProperty = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ObjectProperty<Scenario> selectedScenario = new SimpleObjectProperty<>();

  @Getter
  private final ObjectProperty<Toggle> selectedSide = new SimpleObjectProperty<>();

  private final Events events;
  private final Navigate navigate;

  @Inject
  ScenarioViewModel(final Events events,
                    final Navigate navigate,
                    final ScenarioService scenarioService) {
    this.events = events;
    this.navigate = navigate;

    var scenarios = scenarioService.get();
    scenariosProperty.setValue(FXCollections.observableList(scenarios));

    selectedScenario.addListener((observable, oldValue, newValue) -> setSelectedScenario(newValue));
    selectedSide.addListener((observable, oldValue, newValue) -> setSelectedSide(newValue));
  }

  public void goBack(final Stage stage) {
    navigate.goPrev(NewGameView.class, stage);
  }

  public void continueOn(final Stage stage) {
    log.info("continue");
    events.getSaveGameEvents().fire(new SaveGameEvent());
  }

  private void setSelectedScenario(final Scenario scenario) {
    if (scenario != null) { // can be null is listview is un-staged?
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
}
