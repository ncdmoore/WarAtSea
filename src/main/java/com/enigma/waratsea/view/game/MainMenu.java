package com.enigma.waratsea.view.game;


import com.enigma.waratsea.viewmodel.game.MainMenuViewModel;
import com.google.inject.Inject;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import static com.enigma.waratsea.model.squadron.DeploymentState.AT_AIRFIELD;
import static com.enigma.waratsea.model.squadron.DeploymentState.NOT_DEPLOYED;
import static com.enigma.waratsea.model.squadron.DeploymentState.ON_SHIP;

public class MainMenu {
  private final MainMenuViewModel mainMenuViewModel;

  @Inject
  public MainMenu(final MainMenuViewModel mainMenuViewModel) {
    this.mainMenuViewModel = mainMenuViewModel;
  }

  public MenuBar getMenuBar(final Stage stage) {
    var menuBar = new MenuBar();
    var fileMenu = buildFileMenu(stage);
    var oobMenu = buildOrderOfBattleMenu();

    menuBar.getMenus()
        .addAll(fileMenu, oobMenu);

    // Menu will appear in Mac system menu area like all other mac applications.
    menuBar.setUseSystemMenuBar(true);

    return menuBar;
  }

  private Menu buildFileMenu(final Stage stage) {
    var fileMenu = new Menu("File");
    var quitWarAtSeaMenuItem = new MenuItem("Quit");

    quitWarAtSeaMenuItem.setOnAction(event -> mainMenuViewModel.quitWarAtSea(stage));

    fileMenu.getItems()
        .add(quitWarAtSeaMenuItem);

    return fileMenu;
  }

  private Menu buildOrderOfBattleMenu() {
    var orderOfBattleMenu = new Menu("OOB");
    var airfieldSquadronsMenuItem = new MenuItem("Airfield Squadrons");
    var taskForceSquadronsMenuItem = new MenuItem("Task Force Squadrons");
    var notDeployedSquadronsMenuItem = new MenuItem("Reserve Squadrons");
    var shipsMenuItem = new MenuItem("Ships");

    airfieldSquadronsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showSquadrons(AT_AIRFIELD));
    airfieldSquadronsMenuItem.disableProperty().bind(mainMenuViewModel.getSquadronsPresent(AT_AIRFIELD));

    taskForceSquadronsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showSquadrons(ON_SHIP));
    taskForceSquadronsMenuItem.disableProperty().bind(mainMenuViewModel.getSquadronsPresent(ON_SHIP));

    notDeployedSquadronsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showSquadrons(NOT_DEPLOYED));
    notDeployedSquadronsMenuItem.disableProperty().bind(mainMenuViewModel.getSquadronsPresent(NOT_DEPLOYED));

    shipsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showShips());

    orderOfBattleMenu.getItems()
        .addAll(airfieldSquadronsMenuItem, taskForceSquadronsMenuItem, notDeployedSquadronsMenuItem, shipsMenuItem);

    return orderOfBattleMenu;
  }
}
