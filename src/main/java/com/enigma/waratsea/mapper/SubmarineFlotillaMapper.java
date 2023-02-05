package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.SubmarineFlotilla;
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
public abstract class SubmarineFlotillaMapper {
  public static final SubmarineFlotillaMapper INSTANCE = Mappers.getMapper(SubmarineFlotillaMapper.class);

  @Inject
  public ShipService shipService;

  abstract public List<SubmarineFlotilla> entitiesToModels(List<SubmarineFlotillaEntity> entities);

  abstract public Set<SubmarineFlotillaEntity> modelsToEntities(Set<SubmarineFlotilla> models);

  abstract public SubmarineFlotilla toModel(final SubmarineFlotillaEntity submarineFlotillaEntity);

  abstract public SubmarineFlotillaEntity toEntity(final SubmarineFlotilla submarineFlotilla);

  Set<Ship> mapSubs(final Set<Id> subIds) {
    return Optional.ofNullable(subIds)
        .map(ids -> shipService.get(subIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Ship> subs) {
    return Optional.ofNullable(subs)
        .map(this::getShipIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getShipIds(final Set<Ship> subs) {
    return subs.stream()
        .map(Ship::getId)
        .collect(Collectors.toSet());
  }
}
