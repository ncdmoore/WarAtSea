package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.model.Type;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.OrderOfBattleSummaryViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Stream;

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
    var horizontalLine = new Separator();
    var instructionLabel = new Label("Royal Navy and Royal Air force OOB :");
    instructionLabel.getStyleClass().add("instruction");

    var flag = new ImageView();
    flag.imageProperty().bind(orderOfBattleSummaryViewModel.getFlag());

    var tabPane = buildOobTabs();

    var hBox = new HBox(flag, tabPane);
    hBox.setId("main-pane-hbox");

    var vBox = new VBox(horizontalLine, instructionLabel, hBox);
    vBox.setId("main-pane");

    return vBox;
  }

  private Node buildOobTabs() {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var navalTab = new Tab("Naval");

    navalTab.setContent(buildNavalTab());

    var airTab = new Tab("Air");

    tabPane.getTabs().addAll(navalTab, airTab);

    return tabPane;
  }

  private Node buildNavalTab() {
    ListView<TaskForce> taskForces = new ListView<>();

    var taskForceList = buildTaskForceList(taskForces);
    var taskForceDetails = buildTaskForceDetails(taskForces);

    var taskForceHBox = new HBox(taskForceList, taskForceDetails);
    taskForceHBox.setId("task-force-pane-hbox");

    taskForces.getSelectionModel().selectFirst();

    return taskForceHBox;
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

  private Node buildTaskForceDetails(final ListView<TaskForce> taskForces) {
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

  private Node buildShipSummary(final GridPane gridPane) {
    var summaryLabel = new Label("Ship Summary");
    var horizontalLine = new Separator();

    gridPane.setId("details-grid");

    var vBox = new VBox(summaryLabel, horizontalLine, gridPane);
    vBox.setId("details-grid-vbox");

    return vBox;
  }

  private Node buildSquadronSummary(final GridPane gridPane) {
    var summaryLabel = new Label("Squadron Summary");
    var horizontalLine = new Separator();

    gridPane.setId("details-grid");

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

  private <T extends Type> void displaySummary(final Stream<Map.Entry<T, Integer>> summary, final GridPane gridPane) {
    gridPane.getChildren().clear();

    int[] row = {0}; // The array hack to allow counter in forEach
    summary.forEach(entry -> {
          var typeText = new Text(entry.getKey().getValue());
          var countText = new Text(entry.getValue() + "");
          gridPane.add(typeText, 0, row[0]);
          gridPane.add(countText, 1, row[0]);
          row[0]++;
        }
    );

    if (row[0] == 0) {
      gridPane.add(new Text("No squadrons"), 0 , 0);
    }
  }
}
