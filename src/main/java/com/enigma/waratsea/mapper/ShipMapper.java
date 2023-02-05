package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.*;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.*;
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
  @SubclassMapping(source = CapitalShipEntity.class, target = CapitalShip.class)
  @SubclassMapping(source = SurfaceShipEntity.class, target = SurfaceShip.class)
  @SubclassMapping(source = SubmarineEntity.class, target = Submarine.class)
  abstract public Ship toModel(final ShipEntity shipEntity);

  abstract public AircraftCarrier toAircraftCarrier(final AircraftCarrierEntity aircraftCarrierEntity);
  abstract public CapitalShip toCapitalShip(final CapitalShipEntity capitalShipEntity);
  abstract public SurfaceShip toSurfaceShip(final SurfaceShipEntity surfaceShipEntity);

  @SubclassMapping(source = AircraftCarrier.class, target = AircraftCarrierEntity.class)
  @SubclassMapping(source = CapitalShip.class, target = CapitalShipEntity.class)
  @SubclassMapping(source = SurfaceShip.class, target = SurfaceShipEntity.class)
  @SubclassMapping(source = Submarine.class, target = SubmarineEntity.class)
  abstract public ShipEntity toEntity(final Ship ship);

  abstract public AircraftCarrierEntity toAircraftCarrierEntity(final AircraftCarrier aircraftCarrier);
  abstract public CapitalShipEntity toCapitalShipEntity(final CapitalShip capitalShip);
  abstract public SurfaceShipEntity toSurfaceShipEntity(final SurfaceShip surfaceShip);

  Set<Squadron> mapSquadrons(final Set<Id> squadronIds) {
    return Optional.ofNullable(squadronIds)
        .map(ids -> squadronService.get(squadronIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Squadron> squadrons) {
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
