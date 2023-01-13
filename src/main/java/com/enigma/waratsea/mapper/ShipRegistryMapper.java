package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.ShipRegistry;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShipRegistryMapper {
  public static final ShipRegistryMapper INSTANCE = Mappers.getMapper(ShipRegistryMapper.class);

  @Inject
  @SuppressWarnings("unused")
  private SquadronService squadronService;

  abstract public ShipRegistry toModel(ShipRegistryEntity entity);

  Set<Squadron> mapSquadrons(final Set<Id> squadronIds) {
    return Optional.ofNullable(squadronIds)
        .map(Ids -> squadronService.get(squadronIds))
        .orElse(new HashSet<>());
  }
}
