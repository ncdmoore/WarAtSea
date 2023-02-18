package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.AircraftCarrierEntity;
import com.enigma.waratsea.entity.ship.CapitalShipEntity;
import com.enigma.waratsea.entity.ship.MotorTorpedoBoatEntity;
import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.entity.ship.SubmarineEntity;
import com.enigma.waratsea.entity.ship.SurfaceShipEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.AircraftCarrier;
import com.enigma.waratsea.model.ship.CapitalShip;
import com.enigma.waratsea.model.ship.MotorTorpedoBoat;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.Submarine;
import com.enigma.waratsea.model.ship.SurfaceShip;
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
  private SquadronService squadronService;

  @SubclassMapping(source = AircraftCarrierEntity.class, target = AircraftCarrier.class)
  @SubclassMapping(source = CapitalShipEntity.class, target = CapitalShip.class)
  @SubclassMapping(source = SurfaceShipEntity.class, target = SurfaceShip.class)
  @SubclassMapping(source = SubmarineEntity.class, target = Submarine.class)
  @SubclassMapping(source = MotorTorpedoBoatEntity.class, target = MotorTorpedoBoat.class)
  public abstract Ship toModel(ShipEntity shipEntity);

  public abstract AircraftCarrier toAircraftCarrier(AircraftCarrierEntity aircraftCarrierEntity);

  public abstract CapitalShip toCapitalShip(CapitalShipEntity capitalShipEntity);

  public abstract SurfaceShip toSurfaceShip(SurfaceShipEntity surfaceShipEntity);

  @SubclassMapping(source = AircraftCarrier.class, target = AircraftCarrierEntity.class)
  @SubclassMapping(source = CapitalShip.class, target = CapitalShipEntity.class)
  @SubclassMapping(source = SurfaceShip.class, target = SurfaceShipEntity.class)
  @SubclassMapping(source = Submarine.class, target = SubmarineEntity.class)
  @SubclassMapping(source = MotorTorpedoBoat.class, target = MotorTorpedoBoatEntity.class)
  public abstract ShipEntity toEntity(Ship ship);

  public abstract AircraftCarrierEntity toAircraftCarrierEntity(AircraftCarrier aircraftCarrier);

  public abstract CapitalShipEntity toCapitalShipEntity(CapitalShip capitalShip);

  public abstract SurfaceShipEntity toSurfaceShipEntity(SurfaceShip surfaceShip);

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
