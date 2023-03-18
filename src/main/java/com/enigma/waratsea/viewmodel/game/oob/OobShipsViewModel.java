package com.enigma.waratsea.viewmodel.game.oob;

import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.model.statistics.ProbabilityVisitor;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.StatisticsService;
import com.google.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.Getter;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OobShipsViewModel {
  private final GameService gameService;

  @Getter
  private final StringProperty side = new SimpleStringProperty();

  private  Map<ShipType, BooleanProperty> shipTypePresent = new HashMap<>();

  @Getter
  private  Map<ShipType, ListProperty<Ship>> ships = new HashMap<>();

  @Getter
  private final ProbabilityVisitor probability;

  @Inject
  public OobShipsViewModel(final GameService gameService,
                           final StatisticsService statisticsService) {
    this.gameService = gameService;
    this.probability = statisticsService.getSuccessRate();

    setSide();
    setShips();
  }

  public ListProperty<Ship> getShipType(final ShipType shipType) {
    return ships.getOrDefault(shipType, new SimpleListProperty<>());
  }

  public BooleanProperty isShipTypePresent(final ShipType shipType) {
    return shipTypePresent.getOrDefault(shipType, new SimpleBooleanProperty(false));
  }

  private void setSide() {
    var game = gameService.getGame();
    var humanSide = game.getHumanSide();
    side.setValue(humanSide.getPossessive());
  }

  private void setShips() {
    ShipType.stream()
        .forEach(this::setShipsForShipType);
  }

  private void setShipsForShipType(final ShipType shipType) {
    ships = gameService.getGame()
        .getHuman()
        .getTaskForces()
        .stream()
        .flatMap(taskForce -> taskForce.getShips().stream())
        .collect(Collectors.groupingBy(Ship::getType))
        .entrySet()
        .stream()
        .map(this::sortShips)
        .map(this::convertToProperty)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    shipTypePresent = ships.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, this::buildBooleanProperty));
  }

  private Map.Entry<ShipType, List<Ship>> sortShips(final Map.Entry<ShipType, List<Ship>> entry) {
    var sortedList = entry.getValue()
        .stream()
        .sorted()
        .toList();

    return new AbstractMap.SimpleEntry<>(entry.getKey(), sortedList);
  }

  private Map.Entry<ShipType, ListProperty<Ship>> convertToProperty(final Map.Entry<ShipType, List<Ship>> entry) {
    var shipList = buildListProperty(entry.getValue());

    return new AbstractMap.SimpleEntry<>(entry.getKey(), shipList);
  }

  private ListProperty<Ship> buildListProperty(final List<Ship> shipsOfType) {
    return new SimpleListProperty<>(FXCollections.observableList(shipsOfType));
  }

  private BooleanProperty buildBooleanProperty(final Map.Entry<ShipType, ListProperty<Ship>> entry) {
    return new SimpleBooleanProperty(!entry.getValue().isEmpty());
  }
}
