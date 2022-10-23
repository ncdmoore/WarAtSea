package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.model.*;
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
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class RegionServiceImpl implements RegionService {
  private final RegionRepository regionRepository;
  private final GameService gameService;
  private final RegionMapper regionMapper;

  private Map<Side, List<Region>> regions;

  private final Map<Nation, Map<String, Region>> airfields = new HashMap<>();

  @Inject
  public RegionServiceImpl(final Events events,
                           final RegionRepository regionRepository,
                           final RegionMapper regionMapper,
                           final GameService gameService) {
    this.regionRepository = regionRepository;
    this.regionMapper = regionMapper;
    this.gameService = gameService;

    events.getLoadMapEvent().register(this::handleLoadMapEvent);
  }

  @Override
  public Region getAirfieldRegion(Nation nation, String airfieldId) {
    return airfields.get(nation)
        .get(airfieldId);
  }

  private void handleLoadMapEvent(final LoadMapEvent event) {
    log.info("RegionServiceImpl receives load map event.");

    createAllRegions();
    indexAllAirfields();
  }

  private void createAllRegions() {
    var mapName = gameService.getGame().getScenario().getMap();

    regions = Side.stream()
        .map(side -> new Id(side, mapName))
        .map(this::createRegions)
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private Pair<Side, List<Region>> createRegions(final Id mapId) {
    var regions = regionRepository.get(mapId)
        .stream()
        .map(regionMapper::toModel)
        .toList();

    return new Pair<>(mapId.getSide(), regions);
  }

  private void indexAllAirfields() {
    regions.values()
        .stream()
        .flatMap(Collection::stream)
        .forEach(this::indexRegionAirfields);
  }

  private void indexRegionAirfields (final Region region) {
    var nation = region.getNation();

    airfields.computeIfAbsent(nation, n -> new HashMap<>());

    region.getAirfields()
        .stream()
        .map(Airfield::getId)
        .forEach(id -> airfields.get(nation).putIfAbsent(id, region));
  }
}
