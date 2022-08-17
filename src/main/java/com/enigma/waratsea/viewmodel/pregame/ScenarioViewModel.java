package com.enigma.waratsea.viewmodel.pregame;

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

    @Inject
    public ScenarioViewModel(final Navigate navigate) {
        this.navigate = navigate;
    }

    public void goBack(final Stage stage) {
        navigate.goPrev(ScenarioView.class, stage);
    }

    public void continueOn(final Stage stage) {
        log.info("continue");
    }
}
