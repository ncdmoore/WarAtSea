package com.enigma.waratsea.viewmodel.game.oob;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.squadron.DeploymentState;
import com.enigma.waratsea.model.squadron.Squadron;
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
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class OobSquadronsViewModel {
  private final GameService gameService;
  private final StatisticsService statisticsService;
  private DeploymentState deploymentState;

  @Getter
  private final StringProperty side = new SimpleStringProperty();

  @Getter
  private Set<Nation> nations;

  private final Map<Nation, Map<AircraftType, BooleanProperty>> aircraftTypePresent = new HashMap<>();

  @Getter
  private final Map<Nation, Map<AircraftType, ListProperty<Squadron>>> squadrons = new HashMap<>();

  public void init(final DeploymentState filter) {
    deploymentState = filter;

    setSide();
    setNations();
    setSquadrons();
    updateNations();
  }

  public ListProperty<Squadron> getAircraftTypeSquadrons(final Nation nation, final AircraftType aircraftType) {
    return squadrons.get(nation)
        .getOrDefault(aircraftType, new SimpleListProperty<>());
  }

  public BooleanProperty isAircraftTypePresent(final Nation nation, final AircraftType aircraftType) {
    return aircraftTypePresent.get(nation)
        .getOrDefault(aircraftType, new SimpleBooleanProperty(true));
  }

  public ProbabilityVisitor getProbability() {
    return statisticsService.getSuccessRate();
  }

  private void setNations() {
    nations = determineNations();
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

  private void updateNations() {
    squadrons.forEach((nation, squadronMap) -> {
      if (squadronMap.isEmpty()) {
        nations.remove(nation);
      }
    });
  }

  private void setSquadronsForNation(final Nation nation) {
    var squadronTypeMap = gameService.getGame()
        .getHuman()
        .getSquadrons(nation)
        .stream()
        .filter(s -> s.getDeploymentState() == deploymentState)
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

  private Set<Nation> determineNations() {
    var playerNations = gameService.getGame()
        .getHuman()
        .getNations();

    return new HashSet<>(playerNations);
  }
}
