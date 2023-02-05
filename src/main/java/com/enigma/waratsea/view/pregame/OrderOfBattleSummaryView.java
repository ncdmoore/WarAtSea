package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.SubmarineFlotilla;
import com.enigma.waratsea.model.Type;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.OrderOfBattleSummaryViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.enigma.waratsea.model.squadron.DeploymentState.ON_SHIP;

@Slf4j
public class OrderOfBattleSummaryView implements View {
  private static final String CSS_FILE = "orderOfBattleSummary.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final OrderOfBattleSummaryViewModel orderOfBattleSummaryViewModel;

  @Inject
  public OrderOfBattleSummaryView(final @Named("View") Props props,
                                  final ResourceProvider resourceProvider,
                                  final OrderOfBattleSummaryViewModel orderOfBattleSummaryViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.orderOfBattleSummaryViewModel = orderOfBattleSummaryViewModel;
  }

  @Override
  public void display(Stage stage) {
    var titlePane = buildTitle();
    var mainPane = buildMain();
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
    var title = new Label("Order of Battle Summary");
    title.setId("title");

    var titlePane = new HBox(title);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildMain() {
    var side = orderOfBattleSummaryViewModel.getSide()
        .getValue()
        .toLower();

    var topHorizontalLine = new Separator();

    var instructionString = props.getString(side + ".oob.title");
    var instructionLabel = new Label(instructionString);
    instructionLabel.getStyleClass().add("instruction");

    var leftFlag = new ImageView();
    leftFlag.imageProperty().bind(orderOfBattleSummaryViewModel.getFlag());

    var tabPane = buildOobTabs();

    var rightFlag = new ImageView();
    rightFlag.imageProperty().bind(orderOfBattleSummaryViewModel.getFlag());

    var hBox = new HBox(leftFlag, tabPane, rightFlag);
    hBox.setId("main-pane-hbox");

    var bottomHorizontalLine = new Separator();
    bottomHorizontalLine.setId("main-bottom-line");

    var vBox = new VBox(topHorizontalLine, instructionLabel, hBox, bottomHorizontalLine);
    vBox.setId("main-pane");

    return vBox;
  }

  private Node buildOobTabs() {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var taskForceIcon = resourceProvider.getAppImageView(props.getString("anchor.small.image"));
    var subIcon = resourceProvider.getAppImageView(props.getString("anchor.small.image"));
    var aircraftIcon = resourceProvider.getAppImageView(props.getString("aircraft.small.image"));

    var taskForceTab = new Tab("Task Forces");

    taskForceTab.setContent(buildTaskForcesTab());
    taskForceTab.setGraphic(taskForceIcon);

    var subFlotillaTab = new Tab("Submarine Flotillas");

    subFlotillaTab.setContent(buildSubFlotillaTab());
    subFlotillaTab.setGraphic(subIcon);

    var airTab = new Tab("Land Based Squadrons");

    airTab.setContent(buildAirForceTab());
    airTab.setGraphic(aircraftIcon);

    tabPane.getTabs().addAll(taskForceTab, subFlotillaTab, airTab);
    tabPane.setMinWidth(650);

    return tabPane;
  }

  private Node buildTaskForcesTab() {
    ListView<TaskForce> taskForces = new ListView<>();

    var taskForceList = buildTaskForceList(taskForces);
    var taskForceDetails = buildTaskForceDetails(taskForces);

    var taskForceHBox = new HBox(taskForceList, taskForceDetails);
    taskForceHBox.setId("task-force-pane-hbox");

    taskForces.getSelectionModel().selectFirst();

    return taskForceHBox;
  }

  private Node buildSubFlotillaTab() {
    ListView<SubmarineFlotilla> submarineFlotillas = new ListView<>();

    var subFlotillaList = buildSubFlotillaList(submarineFlotillas);
    var subFlotillaDetails = buildSubFlotillaDetails(submarineFlotillas);

    var taskForceHBox = new HBox(subFlotillaList, subFlotillaDetails);
    taskForceHBox.setId("task-force-pane-hbox");

    submarineFlotillas.getSelectionModel().selectFirst();

    return taskForceHBox;
  }

  private Node buildAirForceTab() {
    ListView<Nation> nations = new ListView<>();

    var nationsList = buildNationsList(nations);
    var squadronDetails = buildSquadronDetails(nations);

    var airForceHBox = new HBox(nationsList, squadronDetails);
    airForceHBox.setId("task-force-pane-hbox");

    nations.getSelectionModel().selectFirst();

    return airForceHBox;
  }

  private Node buildTaskForceList(final ListView<TaskForce> taskForces) {
    var taskForceImage = new ImageView();

    taskForceImage.imageProperty().bind(orderOfBattleSummaryViewModel.getTaskForceImage());
    taskForces.itemsProperty().bind(orderOfBattleSummaryViewModel.getTaskForces());

    taskForces.setMaxWidth(props.getInt("pregame.scenario.list.width"));
    taskForces.setMaxHeight(props.getInt("pregame.scenario.list.height"));

    var listPane = new VBox(taskForceImage, taskForces);
    listPane.setId("list-pane");

    return listPane;
  }

  private Node buildSubFlotillaList(final ListView<SubmarineFlotilla> submarineFlotillas) {
    var submarineFlotillaImage = new ImageView();

    submarineFlotillaImage.imageProperty().bind(orderOfBattleSummaryViewModel.getSubmarineFlotillaImage());
    submarineFlotillas.itemsProperty().bind(orderOfBattleSummaryViewModel.getSubmarineFlotillas());

    submarineFlotillas.setMaxWidth(props.getInt("pregame.scenario.list.width"));
    submarineFlotillas.setMaxHeight(props.getInt("pregame.scenario.list.height"));

    var listPane = new VBox(submarineFlotillaImage, submarineFlotillas);
    listPane.setId("list-pane");

    return listPane;
  }

  private Node buildNationsList(final ListView<Nation> nations) {
    var airForceImage = new ImageView();

    airForceImage.imageProperty().bind(orderOfBattleSummaryViewModel.getAirForceImage());
    nations.itemsProperty().bind(orderOfBattleSummaryViewModel.getNations());

    nations.setMaxWidth(props.getInt("pregame.scenario.list.width"));
    nations.setMaxHeight(props.getInt("pregame.scenario.list.height"));

    var listPane = new VBox(airForceImage, nations);
    listPane.setId("list-pane");

    return listPane;
  }

  private Node buildTaskForceDetails(final ListView<TaskForce> taskForces) {
    var descriptionPane = buildDescription(taskForces);
    var summariesPane = buildSummaries(taskForces);

    var vBox = new VBox(descriptionPane, summariesPane);
    vBox.setId("details-main-vbox");

    return vBox;
  }

  private Node buildSubFlotillaDetails(final ListView<SubmarineFlotilla> flotillas) {
    var descriptionPane = buildSubFlotillaDescription(flotillas);

    var vBox = new VBox(descriptionPane);
    vBox.setId("details-main-vbox");

    return vBox;
  }

  private Node buildSquadronDetails(final ListView<Nation> nation) {
    var descriptionPane = buildSquadronDescription(nation);
    var summariesPane = buildSquadronSummaries(nation);

    var vBox = new VBox(descriptionPane, summariesPane);
    vBox.setId("details-main-vbox");

    return vBox;
  }

  private Node buildDescription(final ListView<TaskForce> taskForces) {
    var nameLabel = new Text("Name:");
    var nameValue = new Text();

    var stateLabel = new Text("State:");
    var stateValue = new Label();

    var missionsLabel = new Text("Missions:");
    var missionsValue = new Text();

    var selectedTaskForce = taskForces.getSelectionModel()
        .selectedItemProperty();

    bindTaskForceState(nameValue, selectedTaskForce);
    bindTaskForceState(stateValue, selectedTaskForce);
    bindTaskForceStateColor(stateValue, selectedTaskForce);
    bindTaskForceMissions(missionsValue, selectedTaskForce);

    var gridPane = new GridPane();
    gridPane.add(nameLabel, 0, 0);
    gridPane.add(nameValue, 1, 0);
    gridPane.add(stateLabel, 0, 1);
    gridPane.add(stateValue, 1, 1);
    gridPane.add(missionsLabel, 0, 2);
    gridPane.add(missionsValue, 1, 2);
    GridPane.setValignment(missionsLabel, VPos.TOP);
    gridPane.setId("missions-grid");

    return gridPane;
  }

  private Node buildSquadronDescription(final ListView<Nation> nations) {
    var nationLabel = new Text("Nation:");
    var nationValue = new Text();

    var nation = nations.getSelectionModel()
        .selectedItemProperty();

    bindNation(nationValue, nation);

    var gridPane = new GridPane();
    gridPane.add(nationLabel, 0, 0);
    gridPane.add(nationValue, 1, 0);
    gridPane.setId("missions-grid");

    return gridPane;
  }

  private Node buildSubFlotillaDescription(final ListView<SubmarineFlotilla> flotillas) {
    var nameLabel = new Text("Name:");
    var nameValue = new Text();

    var stateLabel = new Text("State:");
    var stateValue = new Label();

    var subsLabel = new Text("Submarines:");
    var subsValue = new Text();

    var selectedFlotilla = flotillas.getSelectionModel()
        .selectedItemProperty();

    bindFlotillaName(nameValue, selectedFlotilla);
    bindSubFlotillaState(stateValue, selectedFlotilla);
    bindSubFlotillaStateColor(stateValue, selectedFlotilla);
    bindSubFlotillaCount(subsValue, selectedFlotilla);

    var gridPane = new GridPane();
    gridPane.add(nameLabel, 0, 0);
    gridPane.add(nameValue, 1, 0);
    gridPane.add(stateLabel, 0, 1);
    gridPane.add(stateValue, 1, 1);
    gridPane.add(subsLabel, 0, 2);
    gridPane.add(subsValue, 1, 2);
    gridPane.setId("missions-grid");

    return gridPane;
  }

  private Node buildSummaries(final ListView<TaskForce> taskForces) {
    var shipSummaryGrid = new GridPane();
    var squadronSummaryGrid = new GridPane();

    var shipSummaryPane = buildShipSummary(shipSummaryGrid);
    var squadronSummaryPane = buildSquadronSummary(squadronSummaryGrid);

    var hBox = new HBox(shipSummaryPane, squadronSummaryPane);
    hBox.setId("summary-hbox");

    taskForces.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newlySelectedTaskForce) ->
            handleTaskForceSelected(newlySelectedTaskForce, shipSummaryGrid, squadronSummaryGrid));

    return hBox;
  }

  private Node buildSquadronSummaries(final ListView<Nation> nation) {
    var summaryLabel = new Label("Squadron Summary");
    var horizontalLine = new Separator();
    var gridPane = new GridPane();

    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(summaryLabel, horizontalLine, gridPane);
    vBox.setId("details-grid-vbox");

    nation.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newlySelectedNation) -> handleNationSelected(newlySelectedNation, gridPane));

    return vBox;
  }

  private Node buildShipSummary(final GridPane gridPane) {
    var summaryLabel = new Label("Ship Summary");
    var horizontalLine = new Separator();

    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(summaryLabel, horizontalLine, gridPane);
    vBox.setId("details-grid-vbox");

    return vBox;
  }

  private Node buildSquadronSummary(final GridPane gridPane) {
    var summaryLabel = new Label("Squadron Summary");
    var horizontalLine = new Separator();

    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(summaryLabel, horizontalLine, gridPane);
    vBox.setId("details-grid-vbox");

    return vBox;
  }

  private Node buildPushButtons(final Stage stage) {
    var backButton = new Button("Back");
    backButton.setOnAction(event -> orderOfBattleSummaryViewModel.goBack(stage));

    var continueButton = new Button("Continue");
    continueButton.setOnAction(event -> orderOfBattleSummaryViewModel.continueOn(stage));

    var hBox = new HBox(backButton, continueButton);
    hBox.setId("push-buttons-pane");
    return hBox;
  }

  private void handleTaskForceSelected(final TaskForce taskForce, final GridPane shipGrid, final GridPane squadronGrid) {
    var shipSummary = getShipSummary(taskForce);
    var squadronSummary = getSquadronSummary(taskForce);

    displaySummary(shipSummary, shipGrid);
    displaySummary(squadronSummary, squadronGrid);
  }

  private void handleNationSelected(final Nation nation, final GridPane squadronGrid) {
    var squadronSummary = getSquadronSummary(nation);

    displaySummary(squadronSummary, squadronGrid);
  }

  private Stream<Map.Entry<ShipType, Integer>> getShipSummary(final TaskForce taskForce) {
    return taskForce.getShipSummary()
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey());
  }

  private Stream<Map.Entry<AircraftType, Integer>> getSquadronSummary(final TaskForce taskForce) {
    return taskForce.getSquadronSummary()
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey());
  }

  private Stream<Map.Entry<AircraftType, Integer>> getSquadronSummary(final Nation nation) {
    return orderOfBattleSummaryViewModel.getPlayer()
        .getValue()
        .getSquadrons(nation)
        .stream()
        .filter(s ->  s.getDeploymentState() != ON_SHIP)
        .collect(Collectors.groupingBy(squadron -> squadron.getAircraft().getType(),
            Collectors.summingInt(s -> 1)))
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey());
  }

  private <T extends Type> void displaySummary(final Stream<Map.Entry<T, Integer>> summary, final GridPane gridPane) {
    gridPane.getChildren().clear();

    int[] row = {0}; // The array hack to allow counter in forEach
    summary.forEach(entry -> {
          var typeText = new Text(entry.getKey().getValue() + ":");
          var countText = new Text(entry.getValue() + "");
          gridPane.add(typeText, 0, row[0]);
          gridPane.add(countText, 1, row[0]);
          row[0]++;
        }
    );

    if (row[0] == 0) {
      gridPane.add(new Text("No squadrons"), 0, 0);
    }
  }

  private void bindTaskForceState(final Text nameValue, final ReadOnlyObjectProperty<TaskForce> selectedTaskForce) {
    nameValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedTaskForce.getValue())
            .map(TaskForce::getTitle)
            .orElse(""), selectedTaskForce));
  }

  private void bindTaskForceState(final Label stateValue, final ReadOnlyObjectProperty<TaskForce> selectedTaskForce) {
    stateValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedTaskForce.getValue())
            .map(taskForce -> taskForce.getState().getValue())
            .orElse(""), selectedTaskForce));
  }

  private void bindTaskForceStateColor(final Label stateValue, final ReadOnlyObjectProperty<TaskForce> selectedTaskForce) {
    stateValue.textFillProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedTaskForce.getValue())
            .filter(TaskForce::isReserved)
            .map(taskForce -> Color.RED)
            .orElse(Color.GREEN), selectedTaskForce));
  }

  private void bindTaskForceMissions(final Text missionsValue, final ReadOnlyObjectProperty<TaskForce> selectedTaskForce) {
    missionsValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedTaskForce.getValue())
            .map(taskForce -> taskForce.getMissions()
                .stream()
                .map(Mission::getDescription)
                .collect(Collectors.joining("\n")))
            .orElse(""), selectedTaskForce));
  }

  private void bindFlotillaName(final Text nameValue, final ReadOnlyObjectProperty<SubmarineFlotilla> selectedFlotilla) {
    nameValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedFlotilla.getValue())
            .map(flotilla -> flotilla.getId().getName())
            .orElse(""), selectedFlotilla));
  }

  private void bindSubFlotillaState(final Label stateValue, final ReadOnlyObjectProperty<SubmarineFlotilla> selectedFlotilla) {
    stateValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedFlotilla.getValue())
            .map(submarineFlotilla -> submarineFlotilla.getState().getValue())
            .orElse(""), selectedFlotilla));
  }

  private void bindSubFlotillaStateColor(final Label stateValue, final ReadOnlyObjectProperty<SubmarineFlotilla> selectedFlotilla) {
    stateValue.textFillProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedFlotilla.getValue())
            .filter(SubmarineFlotilla::isReserved)
            .map(taskForce -> Color.RED)
            .orElse(Color.GREEN), selectedFlotilla));
  }

  private void bindSubFlotillaCount(final Text subsValue, final ReadOnlyObjectProperty<SubmarineFlotilla> selectedFlotilla) {
    subsValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedFlotilla.getValue())
            .map(flotilla -> flotilla.getSubs().size() + "")
            .orElse(""), selectedFlotilla));
  }

  private void bindNation(final Text nationValue, final ReadOnlyObjectProperty<Nation> selectedNation) {
    nationValue.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedNation.getValue())
            .map(Nation::toString)
            .orElse(""), selectedNation));
  }
}
