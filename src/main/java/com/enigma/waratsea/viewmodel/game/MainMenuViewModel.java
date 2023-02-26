package com.enigma.waratsea.viewmodel.game;

import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.view.game.OobSquadronsView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.stage.Stage;

public class MainMenuViewModel {
  private final Provider<OobSquadronsView> oobSquadronsViewProvider;

  @Inject
  public MainMenuViewModel(final Provider<OobSquadronsView> oobSquadronsViewProvider) {
    this.oobSquadronsViewProvider = oobSquadronsViewProvider;
  }

  public void quitWarAtSea(final Stage stage) {
    stage.close();
  }

  public void showSquadrons(final AirbaseType airbaseType) {
   oobSquadronsViewProvider.get()
       .display(airbaseType);
  }
}
