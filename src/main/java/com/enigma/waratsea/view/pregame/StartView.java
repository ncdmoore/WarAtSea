package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.StartViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;

public class StartView implements View {
  private static final String CSS_FILE = "startView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final StartViewModel startViewModel;

  @Inject
  StartView(final @Named("View") Props props,
            final ResourceProvider resourceProvider,
            final StartViewModel startViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.startViewModel = startViewModel;
  }

  @Override
  public void display(final Stage stage) {
    var mainPane = buildMainPane(stage);

    var sceneWidth = props.getInt("pregame.scene.width");
    var sceneHeight = props.getInt("pregame.scene.height");

    var scene = new Scene(mainPane, sceneWidth, sceneHeight);
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }

  private VBox buildMainPane(final Stage stage) {
    var titlePane = buildTitlePane();
    var buttonPane = buildButtonPane(stage);

    var mainPane = new VBox(titlePane, buttonPane);
    mainPane.setId("main-pane");
    return mainPane;
  }

  private Node buildTitlePane() {
    var flagHBoxWidth = props.getInt("pregame.start.image.width");

    var flagName = props.getString("flag.large.image");
    var alliesFlagImage = resourceProvider.getImage(ALLIES, flagName);
    var alliesFlagImageView = new ImageView(alliesFlagImage);

    var leftRegion = new Region();
    HBox.setHgrow(leftRegion, Priority.ALWAYS);

    var title = new Label(props.getString("game.title"));
    title.setId("title");

    var rightRegion = new Region();
    HBox.setHgrow(rightRegion, Priority.ALWAYS);

    var axisFlagImage = resourceProvider.getImage(AXIS, flagName);
    var axisFlagImageView = new ImageView(axisFlagImage);

    var titlePane = new HBox(alliesFlagImageView, leftRegion, title, rightRegion, axisFlagImageView);
    titlePane.setMaxWidth(flagHBoxWidth);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildButtonPane(final Stage stage) {
    var backgroundImageView = getBackgroundImage();
    var buttons = buildButtons(stage);

    return new StackPane(backgroundImageView, buttons);
  }

  private Node buildButtons(final Stage stage) {

    var newButton = buildButton("New Game");
    newButton.setOnAction(event -> startViewModel.newGame(stage));

    var savedButton = buildButton("Saved Game");
    savedButton.disableProperty().bind(startViewModel.getSavedGamesExist());
    savedButton.setOnAction(event -> startViewModel.savedGame(stage));

    var optionsButton = buildButton("Options");
    optionsButton.setOnAction(event -> startViewModel.options());

    var quitButton = buildButton("Quit Game");
    quitButton.setOnAction(event -> startViewModel.quitGame(stage));

    var commandButtonsVBox = new VBox(newButton, savedButton, optionsButton, quitButton);
    commandButtonsVBox.setId("command-buttons-vbox");

    return commandButtonsVBox;
  }

  private Button buildButton(final String title) {
    var buttonWidth = props.getInt("pregame.start.button.width");

    var button = new Button(title);
    button.setMinWidth(buttonWidth);
    button.setMaxWidth(buttonWidth);

    return button;
  }

  private ImageView getBackgroundImage() {
    return resourceProvider.getGameImageView(props.getString("pregame.start.image"));
  }
}
