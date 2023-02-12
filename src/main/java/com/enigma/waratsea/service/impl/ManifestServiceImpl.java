package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.dto.CargoDto;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadCargoEvent;
import com.enigma.waratsea.mapper.ManifestMapper;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.cargo.Manifest;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.repository.ManifestRepository;
import com.enigma.waratsea.service.ManifestService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
public class ManifestServiceImpl implements ManifestService {
  private final ManifestRepository manifestRepository;
  private final ManifestMapper manifestMapper;

  @Inject
  public ManifestServiceImpl(final Events events,
                             final ManifestRepository manifestRepository,
                             final ManifestMapper manifestMapper) {
    this.manifestRepository = manifestRepository;
    this.manifestMapper = manifestMapper;

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getLoadCargoEvent().register(this::handleLoadCargoEvent);
  }

  private void handleLoadCargoEvent(final LoadCargoEvent loadCargoEvent) {
    log.debug("Handle LoadCargoEvent");

    Side.stream()
        .forEach(this::loadCargo);
  }

  private void loadCargo(final Side side) {
    get(side)
        .forEach(this::loadManifest);
  }

  private List<Manifest> get(final Side side) {
    var entities = manifestRepository.get(side);

    return manifestMapper.entitiesToModels(entities);
  }

  private void loadManifest(final Manifest manifest) {
    var originPort = manifest.getOriginPort();
    var destinationPorts = manifest.getDestinationPorts();
    var cargoDto = new CargoDto(originPort, destinationPorts);

    manifest.getShips()
        .stream()
        .map(Ship::retrieveCargo)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(cargo -> cargo.load(cargoDto));
  }
}
