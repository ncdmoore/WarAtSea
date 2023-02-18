package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.MtbFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.MtbFlotilla;
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
public abstract class MtbFlotillaMapper {
  public static final MtbFlotillaMapper INSTANCE = Mappers.getMapper(MtbFlotillaMapper.class);

  @Inject
  private ShipService shipService;

  public abstract List<MtbFlotilla> entitiesToModels(List<MtbFlotillaEntity> entities);

  public abstract Set<MtbFlotillaEntity> modelsToEntities(Set<MtbFlotilla> models);

  public abstract MtbFlotilla toModel(MtbFlotillaEntity mtbFlotillaEntity);

  public abstract MtbFlotillaEntity toEntity(MtbFlotilla mtbFlotilla);

  Set<Ship> mapBoats(final Set<Id> boatIds) {
    return Optional.ofNullable(boatIds)
        .map(ids -> shipService.get(boatIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Ship> boats) {
    return Optional.ofNullable(boats)
        .map(this::getShipIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getShipIds(final Set<Ship> boats) {
    return boats.stream()
        .map(Ship::getId)
        .collect(Collectors.toSet());
  }
}
