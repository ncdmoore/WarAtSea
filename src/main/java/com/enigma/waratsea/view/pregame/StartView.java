package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.PropsFactory;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.StartViewModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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

import static com.enigma.waratsea.Globals.VIEW_PROPS;

/**
 * This is the game starting screen.
 * It shows the game name and the main game start buttons.
 */
@Singleton
public class StartView implements View {
  private static final String CSS_FILE = "startView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final StartViewModel startViewModel;

  @Inject
  StartView(final PropsFactory propsFactory,
            final ResourceProvider resourceProvider,
            final StartViewModel startViewModel) {
    this.props = propsFactory.create(VIEW_PROPS);
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

    var alliesFlag = resourceProvider.getGameImageView(props.getString("allies.flag.large.image"));

    var leftRegion = new Region();
    HBox.setHgrow(leftRegion, Priority.ALWAYS);

    var title = new Label(props.getString("game.title"));
    title.setId("title");

    var rightRegion = new Region();
    HBox.setHgrow(rightRegion, Priority.ALWAYS);

    var axisFlag = resourceProvider.getGameImageView(props.getString("axis.flag.large.image"));

    var titlePane = new HBox(alliesFlag, leftRegion, title, rightRegion, axisFlag);
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
    savedButton.setOnAction(event -> startViewModel.savedGame());

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
