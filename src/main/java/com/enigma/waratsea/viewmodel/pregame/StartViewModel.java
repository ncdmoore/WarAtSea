package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.events.NewGameEvent;
import com.enigma.waratsea.model.GlobalEvents;
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
    private final Navigate navigate;
    private final GlobalEvents globalEvents;

    @Inject
    public StartViewModel(final Navigate navigate,
                          final GlobalEvents globalEvents) {
        this.navigate = navigate;
        this.globalEvents = globalEvents;
    }

    public void newGame(final Stage stage) {
        globalEvents.getNewGameEvents().fire(new NewGameEvent());
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
