package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.ClearEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.map.Region;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.RegionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class RegionServiceImpl implements RegionService {
  private final RegionRepository regionRepository;
  private final GameService gameService;
  private final RegionMapper regionMapper;

  private Map<Side, List<Region>> regions;
  private final Map<Side, Set<Nation>> nations = new HashMap<>();
  private final Map<Nation, Map<Id, Region>> airfields = new HashMap<>();

  @Inject
  public RegionServiceImpl(final Events events,
                           final RegionRepository regionRepository,
                           final RegionMapper regionMapper,
                           final GameService gameService) {
    this.regionRepository = regionRepository;
    this.regionMapper = regionMapper;
    this.gameService = gameService;

    registerEvents(events);
  }

  @Override
  public Region getAirfieldRegion(final Nation nation, final Id airfieldId) {
    return airfields.get(nation)
        .get(airfieldId);
  }

  @Override
  public Set<Nation> getNations(final Side side) {
    return nations.get(side);
  }

  private void registerEvents(final Events events) {
    events.getStartNewGameEvent().register(this::handleStartNewGameEvent);
    events.getStartSavedGameEvent().register(this::handleStartSavedGameEvent);
    events.getSelectScenarioEvent().register(this::handleScenarioSelectedEvent);
    events.getClearEvent().register(this::handleClearEvent);
    events.getLoadMapEvent().register(this::handleLoadMapEvent);
  }

  private void handleStartNewGameEvent(final StartNewGameEvent startNewGameEvent) {
    log.debug("RegionServiceImpl receives StartNewGameEvent - clears caches");
    clearCaches();
  }

  private void handleStartSavedGameEvent(final StartSavedGameEvent startSavedGameEvent) {
    log.debug("RegionServiceImpl receives StartSavedGameEvent - clears caches");
    clearCaches();
  }

  private void handleScenarioSelectedEvent(final SelectScenarioEvent selectScenarioEvent) {
    log.debug("RegionServiceImpl receives SelectedScenarioEvent - clears caches");
    clearCaches();
  }

  private void handleClearEvent(final ClearEvent clearEvent) {
    log.debug("RegionServiceImpl receives ClearEvent - clears caches");
    clearCaches();
  }

  private void handleLoadMapEvent(final LoadMapEvent event) {
    log.debug("RegionServiceImpl receives LoadMapEvent.");

    getAllRegions();
    getAllNations();
    indexAllAirfields();
  }

  private void getAllRegions() {
    var mapName = gameService.getGame()
        .getScenario()
        .getMap();

    regions = Side.stream()
        .map(side -> new Id(side, mapName))
        .map(this::createRegions)
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private Pair<Side, List<Region>> createRegions(final Id mapId) {
    var side = mapId.getSide();
    var regionsForSide = regionRepository.get(mapId)
        .stream()
        .map(regionMapper::toModel)
        .toList();

    return new Pair<>(side, regionsForSide);
  }

  private void indexAllAirfields() {
    regions.values()
        .stream()
        .flatMap(Collection::stream)
        .forEach(this::indexRegionAirfields);
  }

  private void indexRegionAirfields(final Region region) {
    var nation = region.getNation();

    airfields.computeIfAbsent(nation, n -> new HashMap<>());

    region.getAirfields()
        .stream()
        .map(airfield -> airfield.addNation(nation))
        .map(Airfield::getId)
        .forEach(airfieldId -> airfields.get(nation).putIfAbsent(airfieldId, region));
  }

  private void getAllNations() {
    Side.stream()
        .forEach(side -> nations.computeIfAbsent(side, this::getNationsForSide));
  }

  private Set<Nation> getNationsForSide(final Side side) {
    return regions.get(side)
        .stream()
        .map(Region::getNation)
        .collect(Collectors.toSet());
  }

  private void clearCaches() {
    Optional.ofNullable(regions).ifPresent(Map::clear);
    airfields.clear();
    nations.clear();
  }
}
