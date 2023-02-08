package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.option.AllotmentModification;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.ScenarioSquadronOptionsViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;

public class ScenarioSquadronOptionsView implements View {
  private static final String CSS_FILE = "scenarioSquadronOptions.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final ScenarioSquadronOptionsViewModel scenarioSquadronOptionsViewModel;

  @Inject
  public ScenarioSquadronOptionsView(final @Named("View") Props props,
                                     final ResourceProvider resourceProvider,
                                     final ScenarioSquadronOptionsViewModel scenarioSquadronOptionsViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.scenarioSquadronOptionsViewModel = scenarioSquadronOptionsViewModel;
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
    var title = new Label("Scenario Squadron Options");
    title.setId("title");

    var titlePane = new HBox(title);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildMainPane() {
    var horizontalLine = new Separator();
    var instructionLabel = new Label("Select Squadron Options:");
    instructionLabel.getStyleClass().add("instruction");

    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    tabPane.setMinWidth(650);

    var hBox = new HBox(tabPane);
    hBox.setId("all-nations-hbox-pane");

    scenarioSquadronOptionsViewModel.getOptions()
        .entrySet()
        .stream()
        .map(this::buildNationTab)
        .forEach(nationTab -> tabPane.getTabs().add(nationTab));

    var mainPane = new VBox(horizontalLine, instructionLabel, hBox);
    mainPane.setId("main-pane");

    return mainPane;
  }

  private Tab buildNationTab(final Map.Entry<NationId, Set<AllotmentModification>> entry) {
    var nationId = entry.getKey();
    var nation = nationId.getNation();
    var nationTab = new Tab(nation.toString());

    var imagePane = buildNationImage(entry);
    var optionsPane = buildNationOptions(entry);

    var hBox = new HBox(imagePane, optionsPane);
    hBox.setId("nation-hbox");

    var roundelImageName = nation.toLower() + ".roundel.small.image";
    var roundelImage = resourceProvider.getGameImageView(props.getString(roundelImageName));

    nationTab.setContent(hBox);
    nationTab.setGraphic(roundelImage);

    return nationTab;
  }

  private Node buildNationImage(final Map.Entry<NationId, Set<AllotmentModification>> entry) {
    var nationId = entry.getKey();
    var nation = nationId.getNation();

    var imageName = nation.toLower() + ".allotment.options.image";

    return resourceProvider.getGameImageView(props.getString(imageName));
  }

  private Node buildNationOptions(final Map.Entry<NationId, Set<AllotmentModification>> entry) {
    var nationId = entry.getKey();
    var options = entry.getValue();

    var selectedOption = scenarioSquadronOptionsViewModel.getSelectedOptions()
        .get(nationId);

    var nationValue = new Label(nationId.getNation().toString() + ":");
    nationValue.getStyleClass().add("heading");

    var radioButtonVBox = new VBox();
    radioButtonVBox.setId("radio-button-vbox");

    var optionsGroup = new ToggleGroup();
    selectedOption.bind(optionsGroup.selectedToggleProperty());

    var buttons = options.stream()
        .sorted()
        .map(this::buildRadioButton)
        .map(button -> addToGroup(button, optionsGroup))
        .toList();

    buttons.get(0).setSelected(true);

    buttons.forEach(button -> radioButtonVBox.getChildren().add(button));

    var gridPane = new GridPane();
    gridPane.add(nationValue, 0, 0);
    gridPane.add(radioButtonVBox, 0, 1);
    gridPane.setId("radio-button-grid");

    return gridPane;
  }

  private RadioButton buildRadioButton(final AllotmentModification option) {
    var text = option.getText();
    var id = option.getId();
    var radioButton = new RadioButton(text);
    radioButton.setUserData(id);

    return radioButton;
  }

  private RadioButton addToGroup(final RadioButton radioButton, final ToggleGroup toggleGroup) {
    radioButton.setToggleGroup(toggleGroup);
    return radioButton;
  }

  private Node buildPushButtons(final Stage stage) {
    var backButton = new Button("Back");
    backButton.setOnAction(event -> scenarioSquadronOptionsViewModel.goBack(stage));

    var continueButton = new Button("Continue");
    continueButton.setOnAction(event -> scenarioSquadronOptionsViewModel.continueOn(stage));

    var hBox = new HBox(backButton, continueButton);
    hBox.setId("push-buttons-pane");
    return hBox;
  }
}
