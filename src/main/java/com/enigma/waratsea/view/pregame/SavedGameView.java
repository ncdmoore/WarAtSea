package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.SavedGameViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

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
    var mainPane = buildMainPane();
    var pushButtons = buildPushButtons(stage);

    var overAllPane = new BorderPane();
    overAllPane.setTop(titlePane);
    overAllPane.setCenter(mainPane);
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

  private Node buildMainPane() {
    var horizontalLine = new Separator();
    var instructionLabel = new Label("Select Saved Game:");
    instructionLabel.getStyleClass().add("instruction");
    var gameList = buildGameList();
    var gameDetails = buildGameDetails();

    var gamePaneHBox = new HBox(gameList, gameDetails);
    gamePaneHBox.setId("game-pane-hbox");

    var mainPane = new VBox(horizontalLine, instructionLabel, gamePaneHBox);
    mainPane.setId("main-pane");

    return mainPane;
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

  private Node buildGameList() {
    var selectedGame = savedGameViewModel.getSelectedSavedGame();
    var scenarioImage = new ImageView();

    bindScenarioImage(scenarioImage, selectedGame);

    ListView<Game> games = new ListView<>();
    games.itemsProperty().bind(savedGameViewModel.getSavedGamesProperty());
    selectedGame.bind(games.getSelectionModel().selectedItemProperty());

    games.getSelectionModel().selectFirst();

    games.setMaxWidth(props.getInt("pregame.scenario.list.width"));
    games.setMaxHeight(props.getInt("pregame.scenario.list.height"));

    var listPane = new VBox(scenarioImage, games);
    listPane.setId("list-pane");

    return listPane;
  }

  private Node buildGameDetails() {
    var selectedGame = savedGameViewModel.getSelectedSavedGame();

    var nameLabel = new Text("Scenario:");
    var sideLabel = new Text("Side:");
    var dateLabel = new Text("Date:");
    var turnLabel = new Text("Turn:");
    var weatherLabel = new Text("Weather:");
    var visibilityLabel = new Text("Visibility:");
    var descriptionLabel = new Text("Description:");

    var nameValue = new Text();
    var sideValue = new Text();
    var dateValue = new Text();
    var turnValue = new Text();
    var weatherValue = new Text();
    var visibilityValue = new Text();
    var descriptionValue = new Text();

    descriptionValue.setWrappingWidth(props.getInt("pregame.scenario.description.wrap"));

    bindName(nameValue, selectedGame);
    bindSide(sideValue, selectedGame);
    bindDate(dateValue, selectedGame);
    bindTurn(turnValue, selectedGame);
    bindWeather(weatherValue, selectedGame);
    bindVisibility(visibilityValue, selectedGame);
    bindDescription(descriptionValue, selectedGame);

    var detailsGrid = new GridPane();
    detailsGrid.add(nameLabel, 0, 0);
    detailsGrid.add(nameValue, 1, 0);
    detailsGrid.add(sideLabel, 0, 1);
    detailsGrid.add(sideValue, 1, 1);
    detailsGrid.add(dateLabel, 0, 2);
    detailsGrid.add(dateValue, 1, 2);
    detailsGrid.add(turnLabel, 0, 3);
    detailsGrid.add(turnValue, 1, 3);
    detailsGrid.add(weatherLabel, 0, 4);
    detailsGrid.add(weatherValue, 1, 4);
    detailsGrid.add(visibilityLabel, 0, 5);
    detailsGrid.add(visibilityValue, 1, 5);
    detailsGrid.add(descriptionLabel, 0, 6);
    detailsGrid.add(descriptionValue, 1, 6);
    detailsGrid.setId("details-grid");
    GridPane.setValignment(descriptionLabel, VPos.TOP);

    return detailsGrid;
  }

  private void bindName(final Text nameValue, final ObjectProperty<Game> selectedGame) {
    nameValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(g -> g.getScenario().getTitle())
            .orElse(""), selectedGame));
  }

  private void bindSide(final Text sideValue, final ObjectProperty<Game> selectedGame) {
    sideValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(g -> g.getHumanSide().toString())
            .orElse(""), selectedGame));
  }

  private void bindDate(final Text dateValue, final ObjectProperty<Game> selectedGame) {
    dateValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(this::formatDate)
            .orElse(""), selectedGame));
  }

  private void bindTurn(final Text turnValue, final ObjectProperty<Game> selectedGame) {
    turnValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(this::getTurn )
            .orElse(""), selectedGame));
  }

  private void bindWeather(final Text weatherValue, final ObjectProperty<Game> selectedGame) {
    weatherValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(this::getWeather)
            .orElse(""), selectedGame));
  }

  private void bindVisibility(final Text visibilityValue, final ObjectProperty<Game> selectedGame) {
    visibilityValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(this::getVisibility)
            .orElse(""), selectedGame));
  }

  private void bindDescription(final Text descriptionValue, final ObjectProperty<Game> selectedGame) {
    descriptionValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(g -> g.getScenario().getDescription())
            .orElse(""), selectedGame));
  }

  private void bindScenarioImage(final ImageView scenarioImage, final ObjectProperty<Game> selectedGame) {
    scenarioImage.imageProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedGame.getValue())
            .map(this::getScenarioImage)
            .orElse(null), selectedGame));
  }

  private Image getScenarioImage(final Game game) {
    var scenario = game.getScenario();
    return resourceProvider.getImage(scenario.getName(), scenario.getImage());
  }

  private String formatDate(final Game game) {
    var dateFormat = props.getString("date.format");
    return game
        .getTurn()
        .getDate()
        .format(DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH));
  }
  private String getWeather(final Game game) {
    return game.getWeather().getWeatherType().toString();
  }

  private String getVisibility(final Game game) {
    return game.getWeather().getVisibility().toString();
  }

  private String getTurn(final Game game) {
    var turn = Integer.toString(game.getTurn().getNumber());
    var maxTurns = Integer.toString(game.getScenario().getMaxTurns());
    return String.join(" ", turn, "out of", maxTurns);
  }

}
