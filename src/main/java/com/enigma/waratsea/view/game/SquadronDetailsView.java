package com.enigma.waratsea.view.game;

import com.enigma.waratsea.model.aircraft.AttackType;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

import static com.enigma.waratsea.model.aircraft.AttackType.AIR;
import static com.enigma.waratsea.model.aircraft.AttackType.LAND;
import static com.enigma.waratsea.model.aircraft.AttackType.NAVAL_TRANSPORT;
import static com.enigma.waratsea.model.aircraft.AttackType.NAVAL_WARSHIP;

public class SquadronDetailsView {
  private final Props props;
  private final ResourceProvider resourceProvider;

  @Inject
  public SquadronDetailsView(final @Named("View") Props props,
                             final ResourceProvider resourceProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
  }

  public Node build(final ListView<Squadron> squadrons) {
    var selectedSquadron = squadrons.getSelectionModel()
        .selectedItemProperty();

    var profileImage = new ImageView();

    var imagePane = new HBox(profileImage);

    var tabPane = buildTabPane(selectedSquadron);

    var mainPane = new VBox(imagePane, tabPane);

    bindProfileImage(profileImage, selectedSquadron);

    return mainPane;
  }

  private TabPane buildTabPane(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var detailsTab = buildDetailsTab(selectedSquadron);
    var attackTab = buildAttackTab(selectedSquadron);
    var defenseTab = buildDefenseTab(selectedSquadron);
    var performanceTab = buildPerformanceTab(selectedSquadron);

    tabPane.getTabs().addAll(detailsTab, attackTab, defenseTab, performanceTab);

    return tabPane;
  }

  private Tab buildDetailsTab(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var tab = new Tab("Details");

    var aircraftDetails = buildAircraftDetails(selectedSquadron);
    var squadronDetails = buildSquadronDetails(selectedSquadron);

    var vBox = new VBox(aircraftDetails, squadronDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildAttackTab(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var tab = new Tab("Attack");

    var airAttackGrid = buildAirAttackGrid(selectedSquadron);
    var landAttackGrid = buildAttackGrid(LAND, selectedSquadron);
    var navalTransportAttackGrid = buildAttackGrid(NAVAL_TRANSPORT, selectedSquadron);
    var navalWarshipAttackGrid = buildAttackGrid(NAVAL_WARSHIP, selectedSquadron);

    var gridPane = new GridPane();
    gridPane.add(airAttackGrid, 0, 0);
    gridPane.add(landAttackGrid, 0, 1);
    gridPane.add(navalTransportAttackGrid, 1, 0);
    gridPane.add(navalWarshipAttackGrid, 1, 1);
    gridPane.getStyleClass().addAll("attack-grid", "details-main-pane");

    tab.setContent(gridPane);

    return tab;
  }

  private Tab buildDefenseTab(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var tab = new Tab("Defense");

    var defenseDetails = buildDefenseDetails(selectedSquadron);

    var vBox = new VBox(defenseDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildPerformanceTab(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var tab = new Tab("Performance");

    var performanceDetails = buildPerformanceDetails(selectedSquadron);
    var rangeDetails = buildRangeDetails(selectedSquadron);

    var vBox = new VBox(performanceDetails, rangeDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Node buildAircraftDetails(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Aircraft Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var modelLabel = new Label("Model:");
    var modelValue = new Label();
    var typeLabel = new Label("Type:");
    var typeValue = new Label();

    bindModel(modelValue, selectedSquadron);
    bindType(typeValue, selectedSquadron);

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(modelLabel, 0, row);
    gridPane.add(modelValue, 1, row);
    gridPane.add(typeLabel, 0, ++row);
    gridPane.add(typeValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private Node buildSquadronDetails(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Squadron Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var nameLabel = new Label("Name:");
    var nameValue = new Label();
    var strengthLabel = new Label("Strength:");
    var strengthValue = new Label();
    var serviceLabel = new Label("Service:");
    var serviceValue = new Label();
    var stateLabel = new Label("Status:");
    var stateValue = new Label();
    var configurationLabel = new Label("Configuration:");
    var configurationValue = new Label();
    var airbaseLabel = new Label("Base:");
    var airbaseValue = new Label();

    bindName(nameValue, selectedSquadron);
    bindStrength(strengthValue, selectedSquadron);
    bindService(serviceValue, selectedSquadron);
    bindState(stateValue, selectedSquadron);
    bindConfiguration(configurationValue, selectedSquadron);
    bindAirbase(airbaseValue, selectedSquadron);

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(nameLabel, 0, row);
    gridPane.add(nameValue, 1, row);
    gridPane.add(strengthLabel, 0, ++row);
    gridPane.add(strengthValue, 1, row);
    gridPane.add(serviceLabel, 0, ++row);
    gridPane.add(serviceValue, 1, row);
    gridPane.add(stateLabel, 0, ++row);
    gridPane.add(stateValue, 1, row);
    gridPane.add(configurationLabel, 0, ++row);
    gridPane.add(configurationValue, 1, row);
    gridPane.add(airbaseLabel, 0, ++row);
    gridPane.add(airbaseValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private Node buildAttackGrid(final AttackType attackType, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label(attackType.getValue());
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var modifierLabel = new Label("Modifier:");
    var modifierValue = new Label();

    var factorLabel = new Label("Factor:");
    var factorValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(modifierLabel, 0, row);
    gridPane.add(modifierValue, 1, row);
    gridPane.add(factorLabel, 0, ++row);
    gridPane.add(factorValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindAttackModifier(attackType, modifierValue, selectedSquadron);
    bindAttackFactor(attackType, factorValue, selectedSquadron);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildAirAttackGrid(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Air Attack");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var modifierLabel = new Label("Modifier:");
    var modifierValue = new Label();

    var factorLabel = new Label("Factor:");
    var factorValue = new Label();

    var defensiveLabel = new Label("Defensive:");
    var defensiveValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(modifierLabel, 0, row);
    gridPane.add(modifierValue, 1, row);
    gridPane.add(factorLabel, 0, ++row);
    gridPane.add(factorValue, 1, row);
    gridPane.add(defensiveLabel, 0, ++row);
    gridPane.add(defensiveValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindAttackModifier(AIR, modifierValue, selectedSquadron);
    bindAttackFactor(AIR, factorValue, selectedSquadron);
    bindDefensive(defensiveValue, selectedSquadron);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildDefenseDetails(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Defense Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var frameLabel = new Label("Frame:");
    var frameValue = new Label();
    var fragileLabel = new Label("Fragile:");
    var fragileValue = new Label();

    bindFrame(frameValue, selectedSquadron);
    bindFragile(fragileValue, selectedSquadron);

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(frameLabel, 0, row);
    gridPane.add(frameValue, 1, row);
    gridPane.add(fragileLabel, 0, ++row);
    gridPane.add(fragileValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private Node buildPerformanceDetails(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Performance Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var altitudeLabel = new Label("Altitude Rating:");
    var altitudeValue = new Label();
    var landingTypeLabel = new Label("Landing Type:");
    var landingTypeValue = new Label();

    bindAltitude(altitudeValue, selectedSquadron);
    bindLandingType(landingTypeValue, selectedSquadron);

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(altitudeLabel, 0, row);
    gridPane.add(altitudeValue, 1, row);
    gridPane.add(landingTypeLabel, 0, ++row);
    gridPane.add(landingTypeValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private Node buildRangeDetails(final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    var titleLabel = new Label("Range Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();

    var rangeLabel = new Label("Range:");
    var rangeValue = new Label();
    var enduranceLabel = new Label("Endurance:");
    var enduranceValue = new Label();
    var radiusLabel = new Label("Combat Radius:");
    var radiusValue = new Label();
    var ferryLabel = new Label("Ferry Distance:");
    var ferryValue = new Label();

    bindRange(rangeValue, selectedSquadron);
    bindEndurance(enduranceValue, selectedSquadron);
    bindRadius(radiusValue, selectedSquadron);
    bindFerry(ferryValue, selectedSquadron);

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(rangeLabel, 0, row);
    gridPane.add(rangeValue, 1, row);
    gridPane.add(enduranceLabel, 0, ++row);
    gridPane.add(enduranceValue, 1, row);
    gridPane.add(radiusLabel, 0, ++row);
    gridPane.add(radiusValue, 1, row);
    gridPane.add(ferryLabel, 0, ++row);
    gridPane.add(ferryValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private void bindModel(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getTitle())
            .orElse(""), selectedSquadron));
  }

  private void bindType(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getType().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindName(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(Squadron::getTitle)
            .orElse(""), selectedSquadron));
  }

  private void bindStrength(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getStrength().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindService(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getService().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindState(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getState().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindConfiguration(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getConfiguration().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindAirbase(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAirbase().getTitle())
            .orElse(""), selectedSquadron));
  }

  private void bindAttackModifier(final AttackType attackType,
                                  final Label label,
                                  final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAttack(attackType))
            .map(attack -> attack.getModifier() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindAttackFactor(final AttackType attackType,
                                final Label label,
                                final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAttack(attackType))
            .map(attack -> attack.getFactor() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindDefensive(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAttack(AIR))
            .map(attack -> attack.isDefensive() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindFrame(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getFrame().getFrame() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindFragile(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getFrame().isFragile() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindAltitude(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getAltitude().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindLandingType(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getLanding().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindRange(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getRange() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindEndurance(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getEndurance() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindRadius(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getRadius() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindFerry(final Label label, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getFerryDistance() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindProfileImage(final ImageView image, final ReadOnlyObjectProperty<Squadron> selectedSquadron) {
    image.imageProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getId())
            .map(resourceProvider::getAircraftProfileImage)
            .orElse(null), selectedSquadron));
  }
}
