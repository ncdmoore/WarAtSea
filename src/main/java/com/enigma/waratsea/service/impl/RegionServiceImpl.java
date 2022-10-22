package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Region;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.RegionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class RegionServiceImpl implements RegionService {
  private final RegionRepository regionRepository;
  private final GameService gameService;

  private Map<Side, List<Region>> regions;

  private final Map<Nation, Map<String, Region>> airfields = new HashMap<>();

  @Inject
  public RegionServiceImpl(final Events events,
                           final RegionRepository regionRepository,
                           final GameService gameService) {
    this.regionRepository = regionRepository;
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
        .map(side -> createRegions(side, mapName))
        .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
  }

  private Pair<Side, List<Region>> createRegions(final Side side, final String mapName) {
    var regions = regionRepository.get(side, mapName)
        .stream()
        .map(RegionMapper.INSTANCE::toModel)
        .toList();

    return new Pair<>(side, regions);
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
