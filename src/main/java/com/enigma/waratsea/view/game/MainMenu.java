package com.enigma.waratsea.view.game;


import com.enigma.waratsea.viewmodel.game.MainMenuViewModel;
import com.google.inject.Inject;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainMenu {
  private final MainMenuViewModel mainMenuViewModel;

  @Inject
  public MainMenu(final MainMenuViewModel mainMenuViewModel) {
    this.mainMenuViewModel = mainMenuViewModel;
  }

  public MenuBar getMenuBar(final Stage stage) {
    var menuBar = new MenuBar();
    var fileMenu = buildFileMenu(stage);

    menuBar.getMenus()
        .add(fileMenu);

    // Menu will appear in Mac system menu area like all other mac applications.
    menuBar.setUseSystemMenuBar(true);

    return menuBar;
  }

  private Menu buildFileMenu(final Stage stage) {
    var fileMenu = new Menu("File");
    var quitWarAtSea = new MenuItem("Quit War at Sea");

    quitWarAtSea.setOnAction(event -> mainMenuViewModel.quitWarAtSea(stage));

    fileMenu.getItems()
        .add(quitWarAtSea);

    return fileMenu;
  }
}
