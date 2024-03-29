package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadRegistryEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.mapper.ShipMapper;
import com.enigma.waratsea.mapper.ShipRegistryMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.ship.Commission;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipRegistry;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.repository.ShipRepository;
import com.enigma.waratsea.service.ShipService;
import com.enigma.waratsea.service.SquadronService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Singleton
public class ShipServiceImpl implements ShipService {
  private final ShipRepository shipRepository;
  private final ShipMapper shipMapper;
  private final SquadronService squadronService;

  private final Map<Side, Map<Id, ShipRegistry>> registry = new HashMap<>();

  private final Map<Id, Ship> ships = new HashMap<>();
  private final Map<Id, Ship> shipClasses = new HashMap<>();

  private boolean isNewGame = true;

  @Inject
  public ShipServiceImpl(final Events events,
                         final ShipRepository shipRepository,
                         final ShipMapper shipMapper,
                         final SquadronService squadronService) {
    this.shipRepository = shipRepository;
    this.shipMapper = shipMapper;
    this.squadronService = squadronService;

    registerEvents(events);
  }

  @Override
  public Set<Ship> get(final Set<Id> shipIds) {
    return shipIds.stream()
        .map(this::get)
        .collect(Collectors.toSet());
  }

  @Override
  public Ship get(final Id shipId) {
    return ships.computeIfAbsent(shipId, this::getShip);
  }

  private void registerEvents(final Events events) {
    events.getLoadRegistryEvent().register(this::handleLoadRegistryEvent);
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getSaveGameEvent().register(this::save);
  }

  private void handleLoadRegistryEvent(final LoadRegistryEvent event) {
    log.debug("ShipServiceImpl handle LoadRegistryEvent");

    Side.stream()
        .forEach(side -> registry.computeIfAbsent(side, this::getShipRegistryForSide));
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("ShipServiceImpl handle StartNewGameEvent");

    isNewGame = true;
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("ShipServiceImpl handle StartSavedGameEvent");

    isNewGame = false;
    clearCaches();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    log.debug("ShipServiceImpl handle SelectedScenarioEvent");

    clearCaches();
  }

  private void handleClearEvent(final ClearEvent clearEvent) {
    log.debug("ShipServiceImpl handle ClearEvent");

    clearCaches();
  }

  private void save(final SaveGameEvent saveGameEvent) {
    var gameId = saveGameEvent.getId();

    ships.values()
        .stream()
        .map(shipMapper::toEntity)
        .forEach(ship -> shipRepository.save(gameId, ship));

    log.info("ShipServiceImpl received saveGameEvent save ships for game: '{}'.", gameId);
  }

  private Ship getShip(final Id shipId) {
    var shipRegistry = Optional.ofNullable(getShipRegistry(shipId))
        .orElseThrow(() -> new GameException("Unable to get ship registry for " + shipId));

    var shipType = shipRegistry.getShipType();

    return isNewGame
        ? getFromShipClass(shipId, shipType)
        : getFromRepository(shipId, shipType);
  }

  private Ship getFromShipClass(final Id shipId, final ShipType shipType) {
    var shipClassId = convertShipIdToShipClassId(shipId);

    var shipClass = shipClasses.computeIfAbsent(shipClassId, id -> getFromRepository(id, shipType));

    return buildShip(shipId, shipClass);
  }

  private Ship getFromRepository(final Id shipId, final ShipType shipType) {
    var entity = shipRepository.get(shipId, shipType);

    var model = shipMapper.toModel(entity);

    if (model instanceof Airbase airbase) {
      airbase.getSquadrons().forEach(squadron -> squadron.setAirbase(airbase));
    }

    return model;
  }

  private Ship buildShip(final Id shipId, final Ship shipClass) {
    var side = shipId.getSide();
    var shipRegistry = registry.get(side).get(shipId);
    var newShip = copyShip(shipClass);
    return newShip.commission(buildCommission(shipRegistry));
  }

  private Map<Id, ShipRegistry> getShipRegistryForSide(final Side side) {
    return ShipType.stream()
        .flatMap(shipType -> getShipRegistryForShipType(side, shipType))
        .collect(Collectors.toMap(
            ShipRegistry::getId,
            shipRegistry -> shipRegistry));
  }

  private Stream<ShipRegistry> getShipRegistryForShipType(final Side side, final ShipType shipType) {
    return shipRepository.getRegistry(side, shipType)
        .stream()
        .map(ShipRegistryMapper.INSTANCE::toModel)
        .map(shipRegistry -> shipRegistry.setShipType(shipType));
  }

  private Id convertShipIdToShipClassId(final Id shipId) {
   return getShipRegistry(shipId).getShipClassId();
  }

  private ShipRegistry getShipRegistry(final Id shipId) {
    var side = shipId.getSide();

    return registry.get(side).get(shipId);
  }

  private Ship copyShip(final Ship ship) {
    var gson = new Gson();
    var json = gson.toJson(ship);
    return gson.fromJson(json, ship.getClass());
  }

  private Commission buildCommission(final ShipRegistry shipRegistry) {
    return Commission.builder()
        .id(shipRegistry.getId())
        .shipClassId(shipRegistry.getShipClassId())
        .title(shipRegistry.getTitle())
        .nation(shipRegistry.getNation())
        .squadrons(squadronService.get(shipRegistry.getSquadrons()))
        .build();
  }

  private void clearCaches() {
    ships.clear();
    shipClasses.clear();
  }
}
