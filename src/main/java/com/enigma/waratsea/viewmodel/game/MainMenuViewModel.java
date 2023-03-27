package com.enigma.waratsea.viewmodel.game;

import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.game.oob.OobShipsView;
import com.enigma.waratsea.view.game.oob.OobSquadronsView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MainMenuViewModel {
  private final GameService gameService;
  private final Provider<OobSquadronsView> oobSquadronsViewProvider;
  private final Provider<OobShipsView> oobShipsViewProvider;

  private final Map<AirbaseType, BooleanProperty> squadronsPresent = new HashMap<>();

  @Inject
  public MainMenuViewModel(final GameService gameService,
                           final Provider<OobSquadronsView> oobSquadronsViewProvider,
                           final Provider<OobShipsView> oobShipsViewProvider) {
    this.gameService = gameService;
    this.oobSquadronsViewProvider = oobSquadronsViewProvider;
    this.oobShipsViewProvider = oobShipsViewProvider;

    AirbaseType.stream()
        .forEach(this::setSquadronPresent);
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

  public BooleanProperty getSquadronsPresent(final AirbaseType airbaseType) {
    return squadronsPresent.getOrDefault(airbaseType, new SimpleBooleanProperty(false));
  }

  private void setSquadronPresent(final AirbaseType airbaseType) {
    squadronsPresent.computeIfAbsent(airbaseType, this::determineIfSquadronsPresent);
  }

  private SimpleBooleanProperty determineIfSquadronsPresent(final AirbaseType airbaseType) {
    var value = gameService.getGame()
        .getHuman()
        .getSquadrons(airbaseType)
        .isEmpty();

    return new SimpleBooleanProperty(value);
  }
}
