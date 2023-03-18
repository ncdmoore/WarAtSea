package com.enigma.waratsea.viewmodel.game;

import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.view.game.oob.OobShipsView;
import com.enigma.waratsea.view.game.oob.OobSquadronsView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.stage.Stage;

public class MainMenuViewModel {
  private final Provider<OobSquadronsView> oobSquadronsViewProvider;
  private final Provider<OobShipsView> oobShipsViewProvider;

  @Inject
  public MainMenuViewModel(final Provider<OobSquadronsView> oobSquadronsViewProvider,
                           final Provider<OobShipsView> oobShipsViewProvider) {
    this.oobSquadronsViewProvider = oobSquadronsViewProvider;
    this.oobShipsViewProvider = oobShipsViewProvider;
  }

  public void quitWarAtSea(final Stage stage) {
    stage.close();
  }

  public void showSquadrons(final AirbaseType airbaseType) {
   oobSquadronsViewProvider.get()
       .display(airbaseType);
  }

  public void showShips() {
    oobShipsViewProvider.get()
        .display();
  }
}
