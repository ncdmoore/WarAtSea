package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.SavedGameViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SavedGameView implements View {
  private static final String CSS_FILE = "savedGameView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final SavedGameViewModel savedGameViewModel;

  @Inject
  public SavedGameView(final @Named("View") Props props,
                       final ResourceProvider resourceProvider,
                       final SavedGameViewModel savedGameViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.savedGameViewModel = savedGameViewModel;
  }

  @Override
  public void display(Stage stage) {
    var titlePane = buildTitle();
    var pushButtons = buildPushButtons(stage);

    var overAllPane = new BorderPane();
    overAllPane.setTop(titlePane);
    overAllPane.setBottom(pushButtons);

    var sceneWidth = props.getInt("pregame.scene.width");
    var sceneHeight = props.getInt("pregame.scene.height");

    Scene scene = new Scene(overAllPane, sceneWidth, sceneHeight);
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }

  private Node buildTitle() {
    var title = new Label("Saved Game Selection");
    title.setId("title");

    var titlePane = new HBox(title);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildPushButtons(final Stage stage) {
    var backButton = new Button("Back");
    backButton.setOnAction(event -> savedGameViewModel.goBack(stage));

    var continueButton = new Button("Continue");
    continueButton.setOnAction(event -> savedGameViewModel.continueOn(stage));

    var hBox = new HBox(backButton, continueButton);
    hBox.setId("push-buttons-pane");
    return hBox;
  }
}
