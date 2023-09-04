package com.enigma.waratsea.view.game.oob;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.squadron.DeploymentState;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.game.squadron.SquadronDetailsView;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.game.oob.OobSquadronsViewModel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static javafx.geometry.Orientation.VERTICAL;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.stage.Modality.APPLICATION_MODAL;

@Slf4j
public class OobSquadronsView {
  private static final String CSS_FILE = "oob.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final OobSquadronsViewModel oobSquadronsViewModel;
  private final Provider<SquadronDetailsView> squadronDetailsViewProvider;

  @Inject
  public OobSquadronsView(final @Named("View") Props props,
                          final ResourceProvider resourceProvider,
                          final OobSquadronsViewModel oobSquadronsViewModel,
                          final Provider<SquadronDetailsView> squadronDetailsViewProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.oobSquadronsViewModel = oobSquadronsViewModel;
    this.squadronDetailsViewProvider = squadronDetailsViewProvider;
  }

  public void display(final DeploymentState deploymentState) {
    oobSquadronsViewModel.init(deploymentState);

    var stage = buildStage();

    setScene(stage);
    stage.showAndWait();
  }

  private Stage buildStage() {
    var stage = new Stage();
    stage.initModality(APPLICATION_MODAL);

    bindTitle(stage.titleProperty());

    return stage;
  }

  private void setScene(final Stage stage) {
    var buttonPane = buildButtonsPane(stage);
    var mainPane = buildMainPane();

    var borderPane = new BorderPane();
    borderPane.setBottom(buttonPane);
    borderPane.setCenter(mainPane);

    var width = props.getInt("oob.dialog.width");
    var height = props.getInt("oob.dialog.height");

    var scene = new Scene(borderPane, width, height);
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
  }

  private void bindTitle(final StringProperty title) {
    var side = oobSquadronsViewModel.getSide();

    title.bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(side.getValue())
            .map(value -> value + " Squadrons")
            .orElse(""), side));
  }

  private Node buildMainPane() {
    var nationsTabPane = new TabPane();
    nationsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var nationTabs = oobSquadronsViewModel.getNations()
        .stream()
        .sorted()
        .map(this::buildNationTab)
        .toList();

    nationsTabPane.getTabs()
        .addAll(nationTabs);

    return nationsTabPane;
  }

  private Tab buildNationTab(final Nation nation) {
    var nationPane = buildNationPane(nation);
    var roundelImage = getRoundel(nation);
    var nationTab = new Tab(nation.toString());

    nationTab.setContent(nationPane);
    nationTab.setGraphic(roundelImage);

    return nationTab;
  }

  private Node buildNationPane(final Nation nation) {
    var squadronTypeTabPane = new TabPane();
    squadronTypeTabPane.getStyleClass().add("squadron-type-tab");
    squadronTypeTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var aircraftTypeTabs = AircraftType.stream()
        .sorted()
        .map(aircraftType -> buildAircraftTypeTab(nation, aircraftType))
        .toList();

    squadronTypeTabPane.getTabs()
        .addAll(aircraftTypeTabs);

    return squadronTypeTabPane;
  }

  private Tab buildAircraftTypeTab(final Nation nation, final AircraftType aircraftType) {
    var aircraftTypeTab = new Tab(aircraftType.getValue());

    aircraftTypeTab.disableProperty()
        .bind(oobSquadronsViewModel.isAircraftTypePresent(nation, aircraftType));

    var squadronList = new ListView<Squadron>();
    var configChoices = new ChoiceBox<SquadronConfiguration>();

    var squadronListNode = buildSquadronListNode(nation, aircraftType, squadronList, configChoices);

    var verticalLine = new Separator();
    verticalLine.setOrientation(VERTICAL);

    var probability = oobSquadronsViewModel.getProbability();
    var squadronDetails = squadronDetailsViewProvider.get()
        .build(squadronList, configChoices, probability);

    var hBox = new HBox(squadronListNode, verticalLine, squadronDetails);
    hBox.getStyleClass().add("squadron-type-hbox-pane");

    aircraftTypeTab.setContent(hBox);

    squadronList.getSelectionModel().selectFirst();
    configChoices.getSelectionModel().selectFirst();

    return aircraftTypeTab;
  }

  private Node buildSquadronListNode(final Nation nation,
                                     final AircraftType aircraftType,
                                     final ListView<Squadron> squadronList,
                                     final ChoiceBox<SquadronConfiguration> configChoices) {
    var selectSquadronInstruction = buildInstructionLabel("Select squadron:");
    var selectConfigurationInstruction = buildInstructionLabel("Select configuration:");

    setupSquadronLists(nation, aircraftType, squadronList, configChoices);
    setupConfigurationChoices(squadronList, configChoices);

    var vBox = new VBox(selectSquadronInstruction, squadronList, selectConfigurationInstruction, configChoices);
    vBox.getStyleClass().add("squadron-list-vbox-pane");

    return vBox;
  }

  private void setupSquadronLists(final Nation nation,
                                  final AircraftType aircraftType,
                                  final ListView<Squadron> squadronList,
                                  final ChoiceBox<SquadronConfiguration> configChoices) {
    squadronList.itemsProperty()
        .bind(oobSquadronsViewModel.getAircraftTypeSquadrons(nation, aircraftType));

    squadronList.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, oldValue, newValue) -> handleSquadronSelection(configChoices, newValue));

    squadronList.setMaxWidth(props.getInt("squadron.list.width"));
    squadronList.setMaxHeight(props.getInt("squadron.list.height"));
  }

  private void setupConfigurationChoices(final ListView<Squadron> squadronList,
                                         final ChoiceBox<SquadronConfiguration> configChoices) {
    configChoices.setMaxWidth(props.getInt("squadron.list.width"));
    configChoices.setMaxHeight(props.getInt("squadron.list.height"));

    configChoices.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, oldValue, newValue) -> handleConfigSelection(squadronList, newValue));
  }

  private Node buildButtonsPane(final Stage stage) {
    var okButton = new Button("ok");

    okButton.setOnAction(event -> stage.close());

    var hBox = new HBox(okButton);
    hBox.setAlignment(TOP_CENTER);
    hBox.setId("push-buttons-pane");

    return hBox;
  }

  private void handleSquadronSelection(final ChoiceBox<SquadronConfiguration> configChoices, final Squadron selectedSquadron) {
    configChoices.getItems().clear();
    configChoices.getItems().addAll(selectedSquadron.getAircraft().getConfiguration());
    configChoices.getSelectionModel().selectFirst();
  }

  private void handleConfigSelection(final ListView<Squadron> squadronList, final SquadronConfiguration config) {
    squadronList.getSelectionModel()
        .getSelectedItem()
        .setConfiguration(config);
  }

  private ImageView getRoundel(final Nation nation) {
    var roundelImageName = props.getString("roundel.small.image");
    var roundelImage = resourceProvider.getImage(nation, roundelImageName);
    return new ImageView(roundelImage);
  }

  private Label buildInstructionLabel(final String text) {
    var instructionLabel = new Label(text);
    instructionLabel.getStyleClass().add("instruction");
    return instructionLabel;
  }
}
