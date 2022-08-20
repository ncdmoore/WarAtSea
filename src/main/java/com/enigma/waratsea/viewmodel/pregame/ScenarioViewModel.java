package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.service.ScenarioService;
import com.enigma.waratsea.view.pregame.ScenarioView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Scenario view model.
 */
@Slf4j
@Singleton
public class ScenarioViewModel {
    private final Navigate navigate;
    private final ScenarioService scenarioService;

    @Inject
    public ScenarioViewModel(final Navigate navigate,
                             final ScenarioService scenarioService) {
        this.navigate = navigate;
        this.scenarioService = scenarioService;

        this.scenarioService.get();

    }

    public void goBack(final Stage stage) {
        navigate.goPrev(ScenarioView.class, stage);
    }

    public void continueOn(final Stage stage) {
        log.info("continue");
    }
}
