package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadCargoEvent;
import com.enigma.waratsea.mapper.ManifestMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.cargo.Manifest;
import com.enigma.waratsea.model.ship.Cargo;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.repository.ManifestRepository;
import com.enigma.waratsea.service.impl.ManifestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManifestServiceTest {
  @InjectMocks
  @SuppressWarnings("unused")
  private ManifestServiceImpl manifestService;

  @Mock
  private ManifestRepository manifestRepository;

  @Mock
  private ManifestMapper manifestMapper;

  @Spy
  private Events events;

  private static final int CARGO_CAPACITY = 3;

  @Test
  void shouldLoadCargo() {
    var numberOfShips = 4;

    var entities = buildEntity(numberOfShips);
    var manifest = buildManifest(numberOfShips);

    given(manifestRepository.get(ALLIES)).willReturn(List.of(entities));
    given(manifestMapper.entitiesToModels(List.of(entities))).willReturn(List.of(manifest));

    events.getLoadCargoEvent()
        .fire(new LoadCargoEvent());

    manifest.getShips()
        .forEach(ship -> assertEquals(CARGO_CAPACITY, getShipCargo(ship)));
  }

  private int getShipCargo(final Ship ship) {
    return ship.retrieveCargo()
        .map(Cargo::getLevel)
        .orElse(0);
  }

  private Id generateId(final String shipName) {
    return new Id(ALLIES, shipName + "-transport");
  }

  private Ship buildCargoShip(final String shipName) {
    var cargo = Cargo.builder()
        .capacity(CARGO_CAPACITY)
        .maxCapacity(CARGO_CAPACITY)
        .build();

    return SurfaceShip.builder()
        .id(generateId(shipName))
        .cargo(cargo)
        .build();
  }

  private Manifest buildManifest(final int numShipsToBuild) {
    var ships = IntStream.range(0, numShipsToBuild)
        .mapToObj(String::valueOf)
        .map(this::buildCargoShip)
        .collect(Collectors.toSet());

    return Manifest.builder()
        .ships(ships)
        .build();
  }

  private ManifestEntity buildEntity(final int numShipsToBuild) {
    var ships = IntStream.range(0, numShipsToBuild)
        .mapToObj(String::valueOf)
        .map(this::generateId)
        .collect(Collectors.toSet());

    return ManifestEntity.builder()
        .ships(ships)
        .build();
  }
}
