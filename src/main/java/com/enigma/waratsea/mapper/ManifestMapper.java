package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.cargo.Manifest;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.service.ShipService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330")
public abstract class ManifestMapper {
  public static final ManifestMapper INSTANCE = Mappers.getMapper(ManifestMapper.class);

  @Inject
  public ShipService shipService;

  abstract public List<Manifest> entitiesToModels(List<ManifestEntity> entities);

  abstract public Set<ManifestEntity> modelsToEntities(Set<Manifest> models);

  abstract public Manifest toModel(final ManifestEntity manifestEntity);

  abstract public ManifestEntity toEntity(final Manifest manifest);

  Set<Ship> mapShips(final Set<Id> shipIds) {
    return Optional.ofNullable(shipIds)
        .map(ids -> shipService.get(shipIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Ship> ships) {
    return Optional.ofNullable(ships)
        .map(this::getShipIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getShipIds(final Set<Ship> ships) {
    return ships.stream()
        .map(Ship::getId)
        .collect(Collectors.toSet());
  }
}
