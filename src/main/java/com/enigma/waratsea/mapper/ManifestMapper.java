package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.cargo.Manifest;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.service.ShipService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public abstract class ManifestMapper {
  public static final ManifestMapper INSTANCE = Mappers.getMapper(ManifestMapper.class);

  @Setter
  private ShipService shipService;

  public abstract List<Manifest> entitiesToModels(List<ManifestEntity> entities);

  public abstract Set<ManifestEntity> modelsToEntities(Set<Manifest> models);

  public abstract Manifest toModel(ManifestEntity manifestEntity);

  public abstract ManifestEntity toEntity(Manifest manifest);

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
