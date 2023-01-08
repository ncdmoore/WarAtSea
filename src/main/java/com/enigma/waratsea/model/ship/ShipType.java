package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.entity.ship.AircraftCarrierEntity;
import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.entity.ship.SurfaceShipEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ShipType {
  AIRCRAFT_CARRIER("Aircraft Carrier", AircraftCarrierEntity.class),
  LIGHT_CARRIER("Light Carrier", AircraftCarrierEntity.class),
  SEAPLANE_CARRIER("Seaplane Carrier", AircraftCarrierEntity.class),
  BATTLECRUISER("Battlecruiser", AircraftCarrierEntity.class),
  BATTLESHIP("Battleship", AircraftCarrierEntity.class),
  HEAVY_CRUISER("Heavy Cruiser", AircraftCarrierEntity.class),
  LIGHT_CRUISER("Light Cruiser", AircraftCarrierEntity.class),
  DESTROYER("Destroyer", SurfaceShipEntity.class),
  DESTROYER_ESCORT("Destroyer Escort", SurfaceShipEntity.class),
  FLAK_SHIP("Flak Ship", SurfaceShipEntity.class),
  MINE_LAYER("Mine Layer", SurfaceShipEntity.class),
  MINE_SWEEPER("Mine Sweeper", SurfaceShipEntity.class),
  MTB("MTB", SurfaceShipEntity.class),
  OILER("Oiler", SurfaceShipEntity.class),
  SLOOP("Sloop", SurfaceShipEntity.class),
  TRANSPORT("Transport", SurfaceShipEntity.class),
  SUBMARINE("Submarine", SurfaceShipEntity.class),
  VIRTUAL("Virtual", SurfaceShipEntity.class);

  private final String value;

  private final Class<? extends ShipEntity> clazz;

  public String toLower() {
    return value.replace(' ', '_').toLowerCase(Locale.ROOT);
  }

  public static Stream<ShipType> stream() {
    return Stream.of(ShipType.values());
  }
}
