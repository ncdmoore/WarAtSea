package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.RegionService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Singleton
public class RegionServiceImpl implements RegionService {
  private final RegionRepository regionRepository;
  private final GameService gameService;

  private final Map<Side, List<RegionEntity>> regions = new HashMap<>();

  @Inject
  public RegionServiceImpl(final Events events,
                           final RegionRepository regionRepository,
                           final GameService gameService) {
    this.regionRepository = regionRepository;
    this.gameService = gameService;

    events.getLoadMapEvent().register(this::handleLoadMapEvent);
  }

  private void handleLoadMapEvent(final LoadMapEvent event) {
    log.info("RegionServiceImpl receives load map event.");

    var mapName = gameService.getGame().getScenario().getMap();

    Side.stream()
        .forEach(side -> regions.put(side, regionRepository.get(side, mapName)));

    //todo map to model.
    //
    // 1. create airfieldRepository airfieldEntity airfieldService airfield airbase
    // 2. create portRepository portEntity portService port
    // 3. create mappers for airfieldEntity to airfield and portEntity to port

    // Example airfield flow   airfieldService.get(id) --> airfieldRepository.get(id) --> airfieldEntity to Airfield
    // set airfield in region



  }

}
