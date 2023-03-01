package com.enigma.waratsea.view.game;

import com.enigma.waratsea.model.aircraft.AttackType;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import com.enigma.waratsea.model.statistics.ProbabilityVisitor;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
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
import java.util.concurrent.Callable;

import static com.enigma.waratsea.model.aircraft.AttackType.AIR;
import static com.enigma.waratsea.model.aircraft.AttackType.LAND;
import static com.enigma.waratsea.model.aircraft.AttackType.NAVAL_TRANSPORT;
import static com.enigma.waratsea.model.aircraft.AttackType.NAVAL_WARSHIP;
import static com.enigma.waratsea.model.squadron.SquadronConfiguration.NONE;

public class SquadronDetailsView {
  private final Props props;
  private final ResourceProvider resourceProvider;

  private ReadOnlyObjectProperty<Squadron> selectedSquadron;
  private ReadOnlyObjectProperty<SquadronConfiguration> selectedConfiguration;
  private ProbabilityVisitor probability;

  @Inject
  public SquadronDetailsView(final @Named("View") Props props,
                             final ResourceProvider resourceProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
  }

  public Node build(final ListView<Squadron> squadrons,
                    final ChoiceBox<SquadronConfiguration> configurations,
                    final ProbabilityVisitor probabilityVisitor) {
    setDependentProperties(squadrons, configurations, probabilityVisitor);

    var profileImage = new ImageView();

    var imagePane = new HBox(profileImage);
    imagePane.getStyleClass().add("image-pane");
    imagePane.setMinWidth(props.getInt("oob.dialog.image.width"));
    imagePane.setMinHeight(props.getInt("oob.dialog.image.height"));

    var tabPane = buildTabPane();

    var mainPane = new VBox(imagePane, tabPane);
    mainPane.getStyleClass().add("squadron-details-main-pane");

    bindProfileImage(profileImage);

    return mainPane;
  }

  private void setDependentProperties(final ListView<Squadron> squadrons,
                                      final ChoiceBox<SquadronConfiguration> configurations,
                                      final ProbabilityVisitor probabilityVisitor) {
    selectedSquadron = squadrons.getSelectionModel()
        .selectedItemProperty();

    selectedConfiguration = configurations.getSelectionModel()
        .selectedItemProperty();

    probability = probabilityVisitor;
  }

  private TabPane buildTabPane() {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var detailsTab = buildDetailsTab();
    var attackTab = buildAttackTab();
    var defenseTab = buildDefenseTab();
    var performanceTab = buildPerformanceTab();

    tabPane.getTabs().addAll(detailsTab, attackTab, defenseTab, performanceTab);

    return tabPane;
  }

  private Tab buildDetailsTab() {
    var tab = new Tab("Details");
    var aircraftDetails = buildAircraftDetails();
    var squadronDetails = buildSquadronDetails();
    var vBox = new VBox(aircraftDetails, squadronDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildAttackTab() {
    var tab = new Tab("Attack");

    var airAttackGrid = buildAttackGrid(AIR);
    var landAttackGrid = buildAttackGrid(LAND);
    var navalTransportAttackGrid = buildAttackGrid(NAVAL_TRANSPORT);
    var navalWarshipAttackGrid = buildAttackGrid(NAVAL_WARSHIP);

    var gridPane = new GridPane();
    gridPane.add(airAttackGrid, 0, 0);
    gridPane.add(landAttackGrid, 0, 1);
    gridPane.add(navalTransportAttackGrid, 1, 0);
    gridPane.add(navalWarshipAttackGrid, 1, 1);
    gridPane.getStyleClass().addAll("attack-grid", "details-main-pane");

    tab.setContent(gridPane);

    return tab;
  }

  private Tab buildDefenseTab() {
    var tab = new Tab("Defense");

    var defenseDetails = buildDefenseDetails();
    var vBox = new VBox(defenseDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildPerformanceTab() {
    var tab = new Tab("Performance");

    var performanceDetails = buildPerformanceDetails();
    var rangeDetails = buildRangeDetails();
    var vBox = new VBox(performanceDetails, rangeDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Node buildAircraftDetails() {
    var titleLabel = new Label("Aircraft Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var modelLabel = new Label("Model:");
    var modelValue = new Label();
    var typeLabel = new Label("Type:");
    var typeValue = new Label();

    bindModel(modelValue);
    bindType(typeValue);

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

  private Node buildSquadronDetails() {
    var titleLabel = new Label("Squadron Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var nameLabel = new Label("Name:");
    var nameValue = new Label();
    var strengthLabel = new Label("Strength:");
    var strengthValue = new Label();
    var serviceLabel = new Label("Service:");
    var serviceValue = new Label();
    var stateLabel = new Label("State:");
    var stateValue = new Label();
    var configurationLabel = new Label("Configuration:");
    var configurationValue = new Label();
    var airbaseLabel = new Label("Base:");
    var airbaseValue = new Label();

    bindName(nameValue);
    bindStrength(strengthValue);
    bindService(serviceValue);
    bindState(stateValue);
    bindConfiguration(configurationValue);
    bindAirbase(airbaseValue);

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

  private Node buildAttackGrid(final AttackType attackType) {
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

    if (attackType == AIR) {
      row = addDefensive(gridPane, row);
    }

    var successRateLabel = new Label("Success Rate:");
    var successRateValue = new Label();
    gridPane.add(successRateLabel, 0, ++row);
    gridPane.add(successRateValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindAttackModifier(attackType, modifierValue);
    bindAttackFactor(attackType, factorValue);
    bindSuccessRate(attackType, successRateValue);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private int addDefensive(final GridPane gridPane,
                            final int startRow) {
    var defensiveLabel = new Label("Defensive:");
    var defensiveValue = new Label();

    int row = startRow;
    gridPane.add(defensiveLabel, 0, ++row);
    gridPane.add(defensiveValue, 1, row);

    bindDefensive(defensiveValue);

    return row;
  }

  private Node buildDefenseDetails() {
    var titleLabel = new Label("Defense Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var frameLabel = new Label("Frame:");
    var frameValue = new Label();
    var fragileLabel = new Label("Fragile:");
    var fragileValue = new Label();

    bindFrame(frameValue);
    bindFragile(fragileValue);

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

  private Node buildPerformanceDetails() {
    var titleLabel = new Label("Performance Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var altitudeLabel = new Label("Altitude Rating:");
    var altitudeValue = new Label();
    var landingTypeLabel = new Label("Landing Type:");
    var landingTypeValue = new Label();

    bindAltitude(altitudeValue);
    bindLandingType(landingTypeValue);

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

  private Node buildRangeDetails() {
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

    bindRange(rangeValue);
    bindEndurance(enduranceValue);
    bindRadius(radiusValue);
    bindFerry(ferryValue);

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

  private void bindModel(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getTitle())
            .orElse(""), selectedSquadron));
  }

  private void bindType(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getType().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindName(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(Squadron::getTitle)
            .orElse(""), selectedSquadron));
  }

  private void bindStrength(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getStrength().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindService(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getService().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindState(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getState().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindConfiguration(final Label label) {
    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getConfiguration().getValue() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindAirbase(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAirbase().getTitle())
            .orElse(""), selectedSquadron));
  }

  private void bindAttackModifier(final AttackType attackType,
                                  final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getAttack(attackType).getModifier() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindAttackFactor(final AttackType attackType,
                                final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getAttack(attackType).getFactor() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindSuccessRate(final AttackType attackType,
                               final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getAttack(attackType).accept(probability) + " %")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindDefensive(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAttack(AIR))
            .map(attack -> attack.isDefensive() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindFrame(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getFrame().getFrame() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindFragile(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getFrame().isFragile() + "")
            .orElse(""), selectedSquadron));
  }

  private void bindAltitude(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getAltitude().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindLandingType(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getLanding().getValue())
            .orElse(""), selectedSquadron));
  }

  private void bindRange(final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getRange() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindEndurance(final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getEndurance() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindRadius(final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getRadius() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindFerry(final Label label) {

    Callable<String> bindingFunction = () -> {
      var config = getConfig();

      return Optional.ofNullable(selectedSquadron.getValue())
          .map(squadron -> squadron.setConfiguration(config))
          .map(squadron -> squadron.getFerryDistance() + "")
          .orElse("");
    };

    label.textProperty()
        .bind(Bindings.createStringBinding(bindingFunction, selectedSquadron, selectedConfiguration));
  }

  private void bindProfileImage(final ImageView image) {
    image.imageProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedSquadron.getValue())
            .map(squadron -> squadron.getAircraft().getId())
            .map(resourceProvider::getAircraftProfileImage)
            .orElse(null), selectedSquadron));
  }

  private SquadronConfiguration getConfig() {
    return Optional
        .ofNullable(selectedConfiguration.getValue())
        .orElse(NONE);
  }
}
