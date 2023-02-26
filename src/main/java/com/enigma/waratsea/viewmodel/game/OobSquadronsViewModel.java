package com.enigma.waratsea.viewmodel.game;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.airbase.AirbaseType;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.GameService;
import com.google.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OobSquadronsViewModel {
  private final GameService gameService;
  private AirbaseType airbaseType;

  @Getter
  private final StringProperty side = new SimpleStringProperty();

  @Getter
  private Set<Nation> nations;

  private final Map<Nation, Map<AircraftType, BooleanProperty>> aircraftTypePresent = new HashMap<>();

  @Getter
  private final Map<Nation, Map<AircraftType, ListProperty<Squadron>>> squadrons = new HashMap<>();

  @Inject
  public OobSquadronsViewModel(final GameService gameService) {
    this.gameService = gameService;
  }

  public void init(final AirbaseType filter) {
    airbaseType = filter;

    setSide();
    setNations();
    setSquadrons();
  }

  public ListProperty<Squadron> getAircraftTypeSquadrons(final Nation nation, final AircraftType aircraftType) {
    return squadrons.get(nation)
        .getOrDefault(aircraftType, new SimpleListProperty<>());
  }

  public BooleanProperty isAircraftTypePresent(final Nation nation, final AircraftType aircraftType) {
    return aircraftTypePresent.get(nation)
        .getOrDefault(aircraftType, new SimpleBooleanProperty(true));
  }

  private void setNations() {
    nations = gameService.getGame()
        .getHuman()
        .getNations();

    nations.forEach(this::initNationMaps);
  }

  private void setSide() {
    var game = gameService.getGame();
    var humanSide = game.getHumanSide();
    side.setValue(humanSide.getPossessive());
  }

  private void setSquadrons() {
   nations.forEach(this::setSquadronsForNation);
  }

  private void initNationMaps(final Nation nation) {
    squadrons.computeIfAbsent(nation, k -> new HashMap<>());
    aircraftTypePresent.computeIfAbsent(nation, k -> new HashMap<>());
  }

  private void setSquadronsForNation(final Nation nation) {

    var squadronTypeMap = gameService.getGame()
        .getHuman()
        .getAirbases(airbaseType)
        .stream()
        .flatMap(airfield -> airfield.getSquadrons().stream())
        .filter(squadron -> squadron.ofNation(nation))
        .collect(Collectors.groupingBy(squadron -> squadron.getAircraft().getType()));

    squadronTypeMap
        .forEach((aircraftType, list) -> setSquadronsForAircraftType(nation, aircraftType, list));
  }

  private void setSquadronsForAircraftType(final Nation nation,
                                           final AircraftType aircraftType,
                                           final List<Squadron> squadronsOfType) {
    var aircraftTypeSquadronsMap = squadrons.get(nation);
    var aircraftTypePresentMap = aircraftTypePresent.get(nation);

    var sortedList = squadronsOfType.stream()
        .sorted()
        .toList();

    var squadronList = buildListProperty(sortedList);

    aircraftTypeSquadronsMap.putIfAbsent(aircraftType, squadronList);
    aircraftTypePresentMap.putIfAbsent(aircraftType, new SimpleBooleanProperty(squadronList.isEmpty()));
  }

  private SimpleListProperty<Squadron> buildListProperty(final List<Squadron> squadronsOfType) {
    return new SimpleListProperty<>(FXCollections.observableList(squadronsOfType));
  }
}
