package com.enigma.waratsea.view.game;

import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.google.inject.Inject;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView implements View {
  private static final String CSS_FILE = "mainView.css";

  private final ResourceProvider resourceProvider;
  private final MainMenu mainMenu;

  @Inject
  public MainView(final ResourceProvider resourceProvider,
                  final MainMenu mainMenu) {
    this.resourceProvider = resourceProvider;

    this.mainMenu = mainMenu;
  }

  @Override
  public void display(final Stage stage) {
    var menuBar = buildMenuBar(stage);
    var map = buildMap();

    var overAllPane = new BorderPane();

    overAllPane.setTop(menuBar);
    overAllPane.setCenter(map);

    var screenBounds = getSceneBounds(stage);

    var scene = new Scene(overAllPane, screenBounds.getWidth(), screenBounds.getHeight());
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }

  private Rectangle2D getSceneBounds(final Stage stage) {
    var screenBounds = Screen.getPrimary().getVisualBounds();

    stage.setX(screenBounds.getMinX());
    stage.setY(screenBounds.getMinY());
    stage.setWidth(screenBounds.getWidth());
    stage.setHeight(screenBounds.getHeight());

    return screenBounds;
  }

  private MenuBar buildMenuBar(final Stage stage) {
    return mainMenu.getMenuBar(stage);
  }

  private Node buildMap() {
    var imageView = resourceProvider.getGameImageView("map.png");

    var scrollPane = new ScrollPane();
    scrollPane.setContent(imageView);
    return scrollPane;
  }
}
