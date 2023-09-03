package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.SubmarineFlotilla;
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
public abstract class SubmarineFlotillaMapper {
  public static final SubmarineFlotillaMapper INSTANCE = Mappers.getMapper(SubmarineFlotillaMapper.class);

  @Setter
  private ShipService shipService;

  public abstract List<SubmarineFlotilla> entitiesToModels(List<SubmarineFlotillaEntity> entities);

  public abstract Set<SubmarineFlotillaEntity> modelsToEntities(Set<SubmarineFlotilla> models);

  public abstract SubmarineFlotilla toModel(SubmarineFlotillaEntity submarineFlotillaEntity);

  public abstract SubmarineFlotillaEntity toEntity(SubmarineFlotilla submarineFlotilla);

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
