package com.enigma.waratsea.view.game.oob;

import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.game.ShipDetailsView;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.game.oob.OobShipsViewModel;
import com.google.inject.Inject;
import com.google.inject.Provider;
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

public class OobShipsView {
  private static final String CSS_FILE = "oob.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final OobShipsViewModel oobShipsViewModel;
  private final Provider<ShipDetailsView> shipDetailsViewProvider;

  @Inject
  public OobShipsView(final @Named("View") Props props,
                      final ResourceProvider resourceProvider,
                      final OobShipsViewModel oobShipsViewModel,
                      final Provider<ShipDetailsView> shipDetailsViewProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.oobShipsViewModel = oobShipsViewModel;
    this.shipDetailsViewProvider = shipDetailsViewProvider;
  }

  public void display() {
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

  private Node buildMainPane() {
    var shipTypeTabPane = new TabPane();
    shipTypeTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var shipTypeTabs = ShipType.stream()
        .filter(this::isShipTypePresent)
        .map(this::buildShipTypeTab)
        .toList();

    shipTypeTabPane.getTabs()
        .addAll(shipTypeTabs);

    return shipTypeTabPane;
  }

  private boolean isShipTypePresent(final ShipType shipType) {
    return oobShipsViewModel.isShipTypePresent(shipType).getValue();
  }

  private Tab buildShipTypeTab(final ShipType shipType) {
    var shipTypeTab = new Tab(shipType.getValue());

    var shipList = new ListView<Ship>();

    var shipListNode = buildShipListNode(shipType, shipList);

    var verticalLine = new Separator();
    verticalLine.setOrientation(VERTICAL);

    var shipDetails = shipDetailsViewProvider.get()
        .build(shipList);

    var hBox = new HBox(shipListNode, verticalLine, shipDetails);
    hBox.getStyleClass().add("squadron-type-hbox-pane");

    shipTypeTab.setContent(hBox);

    shipList.getSelectionModel().selectFirst();

    return shipTypeTab;
  }

  private Node buildShipListNode(final ShipType shipType,
                                 final ListView<Ship> shipList) {
    var selectShipInstruction = buildInstructionLabel();

    setupShipList(shipType, shipList);

    var vBox = new VBox(selectShipInstruction, shipList);
    vBox.getStyleClass().add("squadron-list-vbox-pane");

    return vBox;
  }

  private void setupShipList(final ShipType shipType,
                             final ListView<Ship> shipList) {
    shipList.itemsProperty()
        .bind(oobShipsViewModel.getShipType(shipType));

    shipList.setMaxWidth(props.getInt("squadron.list.width"));
    shipList.setMaxHeight(props.getInt("squadron.list.height"));
  }

  private Node buildButtonsPane(final Stage stage) {
    var okButton = new Button("ok");

    okButton.setOnAction(event -> stage.close());

    var hBox = new HBox(okButton);
    hBox.setAlignment(TOP_CENTER);
    hBox.setId("push-buttons-pane");

    return hBox;
  }

  private void bindTitle(final StringProperty title) {
    var side = oobShipsViewModel.getSide();

    title.bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(side.getValue())
            .map(value -> value + " Ships")
            .orElse(""), side));
  }

  private Label buildInstructionLabel() {
    var instructionLabel = new Label("Select Ship");
    instructionLabel.getStyleClass().add("instruction");
    return instructionLabel;
  }
}
