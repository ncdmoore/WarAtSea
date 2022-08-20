package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.ScenarioService;
import com.enigma.waratsea.view.pregame.ScenarioView;
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

    private final Navigate navigate;
    private final GameService gameService;

    @Inject
    public ScenarioViewModel(final Navigate navigate,
                             final GameService gameService,
                             final ScenarioService scenarioService) {
        this.navigate = navigate;
        this.gameService = gameService;

        var scenarios = scenarioService.get();
        scenariosProperty.setValue(FXCollections.observableList(scenarios));

        selectedScenario.addListener((observable, oldValue, newValue) -> setSelectedScenario(newValue));
    }

    public void goBack(final Stage stage) {
        navigate.goPrev(ScenarioView.class, stage);
    }

    public void continueOn(final Stage stage) {
        log.info("continue");
    }

    private void setSelectedScenario(final Scenario scenario) {
        gameService.setScenario(scenario);
    }
}
