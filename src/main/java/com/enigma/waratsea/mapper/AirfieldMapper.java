package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330")
public abstract class AirfieldMapper {
  public static final AirfieldMapper INSTANCE = Mappers.getMapper(AirfieldMapper.class);

  @Inject
  @SuppressWarnings("unused")
  private SquadronService squadronService;

  abstract public Airfield toModel(AirfieldEntity airfieldEntity);
  abstract public AirfieldEntity toEntity(Airfield airfield);

  Set<Squadron> mapSquadrons(final Set<Id> squadronIds) {
    return Optional.ofNullable(squadronIds)
        .map(ids -> squadronService.get(squadronIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapId(final Set<Squadron> squadrons) {
    return Optional.ofNullable(squadrons)
        .map(this::getSquadronIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getSquadronIds(final Set<Squadron> squadrons) {
    return squadrons.stream()
        .map(Squadron::getId)
        .collect(Collectors.toSet());
  }
}
