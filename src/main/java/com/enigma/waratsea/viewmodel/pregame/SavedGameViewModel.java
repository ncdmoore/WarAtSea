package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.view.pregame.SavedGameView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SavedGameViewModel {
  private final Navigate navigate;

  @Inject
  public SavedGameViewModel(final Navigate navigate) {
    this.navigate = navigate;
  }

  public void goBack(final Stage stage) {
    navigate.goPrev(SavedGameView.class, stage);
  }

  public void continueOn(final Stage stage) {
    log.info("continue");
  }
}
