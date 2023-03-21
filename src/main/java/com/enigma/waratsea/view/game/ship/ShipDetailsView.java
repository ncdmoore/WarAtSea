package com.enigma.waratsea.view.game.ship;

import com.enigma.waratsea.dto.ArmourDto;
import com.enigma.waratsea.dto.WeaponsDto;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.ArmourType;
import com.enigma.waratsea.model.ship.Gun;
import com.enigma.waratsea.model.ship.Ship;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.function.Function;

public class ShipDetailsView {
  private final Props props;
  private final ResourceProvider resourceProvider;

  private ReadOnlyObjectProperty<Ship> selectedShip;

  @Inject
  public ShipDetailsView(final @Named("View") Props props,
                         final ResourceProvider resourceProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
  }

  public Node build(final ListView<Ship> ships) {
    setDependentProperties(ships);

    var shipImages = buildImages();
    var imagePane = new HBox(shipImages);
    var tabPane = buildTabPane();

    var mainPane = new VBox(imagePane, tabPane);
    mainPane.getStyleClass().add("squadron-details-main-pane");

    return mainPane;
  }

  private void setDependentProperties(final ListView<Ship> squadrons) {
    selectedShip = squadrons.getSelectionModel()
        .selectedItemProperty();
  }

  private TabPane buildImages() {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var profileTab = buildProfileImageTab();
    var pictureTab = buildPictureImageTab();

    tabPane.getTabs().addAll(profileTab, pictureTab);

    return tabPane;
  }

  private Tab buildProfileImageTab() {
    var profileTab = new Tab("Profile");
    var image = new ImageView();
    var imagePane = buildImagePane(image);

    bindImage(image, resourceProvider::getShipProfileImage);
    profileTab.setContent(imagePane);

    return profileTab;
  }

  private Tab buildPictureImageTab() {
    var profileTab = new Tab("Picture");
    var image = new ImageView();
    var imagePane = buildImagePane(image);

    bindImage(image, resourceProvider::getShipImage);
    profileTab.setContent(imagePane);

    return profileTab;
  }

  private Node buildImagePane(final ImageView image) {
    var imagePane = new HBox(image);

    imagePane.getStyleClass().add("image-pane");
    imagePane.setMinWidth(props.getInt("oob.dialog.ship.image.width"));
    imagePane.setMinHeight(props.getInt("oob.dialog.ship.image.height"));

    return imagePane;
  }

  private void bindImage(final ImageView image, final Function<Id, Image> getImageFunction) {
    image.imageProperty().bind(Bindings.createObjectBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getShipClassId)
            .map(getImageFunction)
            .orElse(null), selectedShip));
  }

  private TabPane buildTabPane() {
    var tabPane = new TabPane();
    tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    var detailsTab = buildDetailsTab();
    var weaponsTab = buildWeaponsTab();
    var armourTab = buildArmourTab();
    var movementTab = buildMovementTab();
    var cargoTab = buildCargoTab();

    tabPane.getTabs().addAll(detailsTab, weaponsTab, armourTab, movementTab, cargoTab);

    return tabPane;
  }

  private Tab buildDetailsTab() {
    var tab = new Tab("Details");
    var shipDetails = buildShipDetails();
    var vBox = new VBox(shipDetails);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildWeaponsTab() {
    var tab = new Tab("Weapons");

    var surfaceWeapons = buildSurfaceWeapons();
    var antiAirWeapons = buildAntiAirWeapons();
    var torpedo = buildTorpedo();
    var asw = buildAsw();

    var gridPane = new GridPane();
    gridPane.add(surfaceWeapons, 0, 0);
    gridPane.add(antiAirWeapons, 0, 1);
    gridPane.add(torpedo, 1, 0);
    gridPane.add(asw, 1, 1);
    gridPane.getStyleClass().addAll("attack-grid", "details-main-pane");

    tab.setContent(gridPane);

    return tab;
  }

  private Tab buildArmourTab() {
    var tab = new Tab("Armour");
    var armour = buildArmour();
    var vBox = new VBox(armour);
    vBox.getStyleClass().addAll("details-main-pane", "details-main-vbox");

    tab.setContent(vBox);

    return tab;
  }

  private Tab buildMovementTab() {
    var tab = new Tab("Movement");

    return tab;
  }

  private Tab buildCargoTab() {
    var tab = new Tab("Cargo");

    return tab;
  }

  private Node buildShipDetails() {
    var titleLabel = new Label("Ship Details");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var nameLabel = new Label("Name:");
    var nameValue = new Label();
    var typeLabel = new Label("Type:");
    var typeValue = new Label();
    var classLabel = new Label("Class:");
    var classValue = new Label();
    var nationLabel = new Label("Nation:");
    var nationValue = new Label();
    var victoryLabel = new Label("Victory Points:");
    var victoryValue = new Label();

    bindString(nameValue, Ship::getTitle);
    bindString(typeValue, ship -> ship.getType().getValue());
    bindString(classValue, Ship::getShipClass);
    bindString(nationValue, ship -> ship.getNation().getValue());
    bindString(victoryValue, ship -> ship.getVictoryPoints() + "");

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(nameLabel, 0, row);
    gridPane.add(nameValue, 1, row);
    gridPane.add(typeLabel, 0, ++row);
    gridPane.add(typeValue, 1, row);
    gridPane.add(classLabel, 0, ++row);
    gridPane.add(classValue, 1, row);
    gridPane.add(nationLabel, 0, ++row);
    gridPane.add(nationValue, 1, row);
    gridPane.add(victoryLabel, 0, ++row);
    gridPane.add(victoryValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().addAll("details-vbox");

    return vBox;
  }

  private Node buildSurfaceWeapons() {
    var titleLabel = new Label("Surface Weapons");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var primaryLabel = new Label("Primary:");
    var primaryValue = new Label();
    var secondaryLabel = new Label("Secondary:");
    var secondaryValue = new Label();
    var tertiaryLabel = new Label("Tertiary:");
    var tertiaryValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(primaryLabel, 0, row);
    gridPane.add(primaryValue, 1, row);
    gridPane.add(secondaryLabel, 0, ++row);
    gridPane.add(secondaryValue, 1, row);
    gridPane.add(tertiaryLabel, 0, ++row);
    gridPane.add(tertiaryValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindGun(primaryValue, WeaponsDto::getPrimary);
    bindGun(secondaryValue, WeaponsDto::getSecondary);
    bindGun(tertiaryValue, WeaponsDto::getTertiary);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildAntiAirWeapons() {
    var titleLabel = new Label("Anti-Air Weapons");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var antiAirLabel = new Label("Anti-air:");
    var antiAirValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(antiAirLabel, 0, row);
    gridPane.add(antiAirValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindGun(antiAirValue, WeaponsDto::getAntiAir);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildTorpedo() {
    var titleLabel = new Label("Torpedoes");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var torpedoLabel = new Label("Torpedo:");
    var torpedoValue = new Label();
    var torpedoRoundsLabel = new Label("Rounds:");
    var torpedoRoundsValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(torpedoLabel, 0, row);
    gridPane.add(torpedoValue, 1, row);
    gridPane.add(torpedoRoundsLabel, 0, ++row);
    gridPane.add(torpedoRoundsValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindTorpedo(torpedoValue);
    bindTorpedoRounds(torpedoRoundsValue);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildAsw() {
    var titleLabel = new Label("ASW");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var aswLabel = new Label("Capable:");
    var aswValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(aswLabel, 0, row);
    gridPane.add(aswValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindAsw(aswValue);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private Node buildArmour() {
    var titleLabel = new Label("Armour");
    titleLabel.getStyleClass().add("heading");

    var horizontalLine = new Separator();
    var primaryLabel = new Label("Primary:");
    var primaryValue = new Label();
    var secondaryLabel = new Label("Secondary:");
    var secondaryValue = new Label();
    var tertiaryLabel = new Label("Tertiary:");
    var tertiaryValue = new Label();
    var antiAirLabel = new Label("Anti-air:");
    var antiAirValue = new Label();
    var hullLabel = new Label("Hull:");
    var hullValue = new Label();
    var deckLabel = new Label("Deck:");
    var deckValue = new Label();

    var row = 0;
    var gridPane = new GridPane();
    gridPane.add(primaryLabel, 0, row);
    gridPane.add(primaryValue, 1, row);
    gridPane.add(secondaryLabel, 0, ++row);
    gridPane.add(secondaryValue, 1, row);
    gridPane.add(tertiaryLabel, 0, ++row);
    gridPane.add(tertiaryValue, 1, row);
    gridPane.add(antiAirLabel, 0, ++row);
    gridPane.add(antiAirValue, 1, row);
    gridPane.add(hullLabel, 0, ++row);
    gridPane.add(hullValue, 1, row);
    gridPane.add(deckLabel, 0, ++row);
    gridPane.add(deckValue, 1, row);
    gridPane.getStyleClass().add("details-grid");

    bindArmour(primaryValue, ArmourDto::getPrimary);
    bindArmour(secondaryValue, ArmourDto::getSecondary);
    bindArmour(tertiaryValue, ArmourDto::getTertiary);
    bindArmour(antiAirValue, ArmourDto::getAntiAir);
    bindArmour(hullValue, ArmourDto::getHull);
    bindDeckArmour(deckValue);

    var vBox = new VBox(titleLabel, horizontalLine, gridPane);
    vBox.getStyleClass().add("details-vbox");

    return vBox;
  }

  private void bindString(final Label label, final Function<Ship, String> getString) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(getString)
            .orElse(""), selectedShip));
  }

  private void bindGun(final Label label, final Function<WeaponsDto, Gun> getGun) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getWeapons)
            .map(getGun)
            .map(gun -> gun.getHealth() + "")
            .orElse("0"), selectedShip));
  }

  private void bindTorpedo(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getWeapons)
            .map(WeaponsDto::getTorpedo)
            .map(torpedo -> torpedo.getHealth() + "")
            .orElse("0"), selectedShip));
  }

  private void bindTorpedoRounds(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getWeapons)
            .map(WeaponsDto::getTorpedo)
            .map(torpedo -> torpedo.getNumber() + "")
            .orElse("0"), selectedShip));
  }

  private void bindAsw(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getWeapons)
            .map(WeaponsDto::isAsw)
            .map(String::valueOf)
            .orElse("false"), selectedShip));
  }

  private void bindArmour(final Label label, final Function<ArmourDto, ArmourType> getArmour) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getArmour)
            .map(getArmour)
            .map(ArmourType::getValue)
            .orElse("None"), selectedShip));
  }

  private void bindDeckArmour(final Label label) {
    label.textProperty().bind(Bindings.createStringBinding(() ->
        Optional.ofNullable(selectedShip.getValue())
            .map(Ship::getArmour)
            .map(ArmourDto::isDeck)
            .map(String::valueOf)
            .orElse("false"), selectedShip));
  }
}
