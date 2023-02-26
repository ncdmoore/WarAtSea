package com.enigma.waratsea.view.game;


import com.enigma.waratsea.viewmodel.game.MainMenuViewModel;
import com.google.inject.Inject;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import static com.enigma.waratsea.model.airbase.AirbaseType.AIRFIELD;
import static com.enigma.waratsea.model.airbase.AirbaseType.SHIP;

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

    airfieldSquadronsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showSquadrons(AIRFIELD));
    taskForceSquadronsMenuItem.setOnAction(actionEvent -> mainMenuViewModel.showSquadrons(SHIP));

    orderOfBattleMenu.getItems()
        .addAll(airfieldSquadronsMenuItem, taskForceSquadronsMenuItem);

    return orderOfBattleMenu;
  }
}
