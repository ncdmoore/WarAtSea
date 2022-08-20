package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.service.GameService;
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
    private final GameService gameService;

    @Inject
    public StartViewModel(final Navigate navigate,
                          final GameService gameService) {
        this.navigate = navigate;
        this.gameService = gameService;
    }

    public void newGame(final Stage stage) {
        gameService.create();
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
