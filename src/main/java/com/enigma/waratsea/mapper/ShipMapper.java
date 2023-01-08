package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.AircraftCarrierEntity;
import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.AircraftCarrier;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.SquadronService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public abstract class ShipMapper {
  public static final ShipMapper INSTANCE = Mappers.getMapper(ShipMapper.class);

  @Inject
  public SquadronService squadronService;

  @SubclassMapping(source = AircraftCarrierEntity.class, target = AircraftCarrier.class)
  abstract public Ship toModel(final ShipEntity shipEntity);

  abstract public AircraftCarrier toAircraftCarrier(final AircraftCarrierEntity aircraftCarrierEntity);

  Set<Squadron> mapSquadrons(final Set<Id> squadronIds) {
    return Optional.ofNullable(squadronIds)
        .map(ids -> squadronService.get(squadronIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapId(final Set<Squadron> squadrons) {
    if (squadrons == null) {
      return Collections.emptySet();
    }

    return squadrons.stream()
        .map(Squadron::getId)
        .collect(Collectors.toSet());
  }
}
