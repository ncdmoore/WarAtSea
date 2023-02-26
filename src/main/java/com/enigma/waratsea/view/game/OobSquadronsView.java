package com.enigma.waratsea.view.game;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.game.OobSquadronsViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

import static javafx.geometry.Orientation.VERTICAL;
import static javafx.geometry.Pos.TOP_CENTER;
import static javafx.stage.Modality.APPLICATION_MODAL;

public class OobSquadronsView {
  private static final String CSS_FILE = "oob.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final OobSquadronsViewModel oobSquadronsViewModel;
  private final SquadronDetailsView squadronDetailsView;

  @Inject
  public OobSquadronsView(final @Named("View") Props props,
                          final ResourceProvider resourceProvider,
                          final OobSquadronsViewModel oobSquadronsViewModel,
                          final SquadronDetailsView squadronDetailsView) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.oobSquadronsViewModel = oobSquadronsViewModel;
    this.squadronDetailsView = squadronDetailsView;
  }

  public void display(final AirbaseType airbaseType) {
    oobSquadronsViewModel.init(airbaseType);

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

    oobSquadronsViewModel.getNations()
        .stream()
        .sorted()
        .map(this::buildNationTab)
        .forEach(tab -> nationsTabPane.getTabs().add(tab));

    return nationsTabPane;
  }

  private Tab buildNationTab(final Nation nation) {
    var nationTab = new Tab(nation.toString());

    var nationPane = buildNationPane(nation);

    var roundelImageName = nation.toLower() + ".roundel.small.image";
    var roundelImage = resourceProvider.getGameImageView(props.getString(roundelImageName));

    nationTab.setContent(nationPane);
    nationTab.setGraphic(roundelImage);

    return nationTab;
  }

  private Node buildNationPane(final Nation nation) {
    var squadronTypeTabPane = new TabPane();
    squadronTypeTabPane.getStyleClass().add("squadron-type-tab");
    squadronTypeTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    AircraftType.stream()
        .sorted()
        .map(aircraftType -> buildAircraftTypeTab(nation, aircraftType))
        .forEach(tab -> squadronTypeTabPane.getTabs().add(tab));

    return squadronTypeTabPane;
  }

  private Tab buildAircraftTypeTab(final Nation nation, final AircraftType aircraftType) {
    var aircraftTypeTab = new Tab(aircraftType.getValue());

    aircraftTypeTab.disableProperty()
        .bind(oobSquadronsViewModel.isAircraftTypePresent(nation, aircraftType));

    var squadronList = new ListView<Squadron>();

    var squadronListNode = buildSquadronListNode(nation, aircraftType, squadronList);

    var verticalLine = new Separator();
    verticalLine.setOrientation(VERTICAL);

    var squadronDetails = squadronDetailsView.build(squadronList);

    var hBox = new HBox(squadronListNode, verticalLine, squadronDetails);
    hBox.getStyleClass().add("squadron-type-hbox-pane");

    aircraftTypeTab.setContent(hBox);

    squadronList.getSelectionModel().selectFirst();

    return aircraftTypeTab;
  }

  private Node buildSquadronListNode(final Nation nation,
                                     final AircraftType aircraftType,
                                     final ListView<Squadron> squadronList) {
    var instruction = new Label("Select squadron:");
    instruction.getStyleClass().add("instruction");

    squadronList.itemsProperty()
        .bind(oobSquadronsViewModel.getAircraftTypeSquadrons(nation, aircraftType));

    var vBox = new VBox(instruction, squadronList);
    vBox.getStyleClass().add("squadron-list-vbox-pane");

    return vBox;
  }


  private Node buildButtonsPane(final Stage stage) {
    var okButton = new Button("ok");

    okButton.setOnAction(event -> stage.close());

    var hBox = new HBox(okButton);
    hBox.setAlignment(TOP_CENTER);
    hBox.setId("push-buttons-pane");

    return hBox;
  }
}
