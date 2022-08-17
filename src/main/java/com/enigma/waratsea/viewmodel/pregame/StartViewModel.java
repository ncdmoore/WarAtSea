package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * The start screen view model.
 */
@Slf4j
@Singleton
public class StartViewModel {
    public final Navigate navigate;

    @Inject
    public StartViewModel(final Navigate navigate) {
        this.navigate = navigate;
    }

    public void newGame(final Stage stage) {
        navigate.goNext(StartView.class, stage);
    }

    public void savedGame() {
        log.info("saved game");
    }

    public void options() {
        log.info("options todo");
    }

    public void quitGame(final Stage stage) {
        stage.close();
    }
}
