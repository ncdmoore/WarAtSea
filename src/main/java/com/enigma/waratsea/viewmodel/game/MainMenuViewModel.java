package com.enigma.waratsea.viewmodel.game;

import com.enigma.waratsea.model.squadron.DeploymentState;
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

  private final Map<DeploymentState, BooleanProperty> squadronsPresent = new HashMap<>();

  @Inject
  public MainMenuViewModel(final GameService gameService,
                           final Provider<OobSquadronsView> oobSquadronsViewProvider,
                           final Provider<OobShipsView> oobShipsViewProvider) {
    this.gameService = gameService;
    this.oobSquadronsViewProvider = oobSquadronsViewProvider;
    this.oobShipsViewProvider = oobShipsViewProvider;

    DeploymentState.stream()
        .forEach(this::setSquadronPresent);
  }

  public void quitWarAtSea(final Stage stage) {
    stage.close();
  }

  public void showSquadrons(final DeploymentState deploymentState) {
   oobSquadronsViewProvider.get()
       .display(deploymentState);
  }

  public void showShips() {
    oobShipsViewProvider.get()
        .display();
  }

  public BooleanProperty getSquadronsPresent(final DeploymentState deploymentState) {
    return squadronsPresent.getOrDefault(deploymentState, new SimpleBooleanProperty(true));
  }

  private void setSquadronPresent(final DeploymentState deploymentState) {
    squadronsPresent.computeIfAbsent(deploymentState, this::determineIfSquadronsPresent);
  }

  private SimpleBooleanProperty determineIfSquadronsPresent(final DeploymentState deploymentState) {
    var present = gameService.getGame()
        .getHuman()
        .getSquadrons()
        .stream()
        .anyMatch(s -> s.getDeploymentState() == deploymentState);

    return new SimpleBooleanProperty(!present);
  }
}
