package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.entity.ship.*;
import com.enigma.waratsea.model.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ShipType implements Type {
  AIRCRAFT_CARRIER("Aircraft Carrier", AircraftCarrierEntity.class),
  LIGHT_CARRIER("Light Carrier", AircraftCarrierEntity.class),
  ESCORT_CARRIER("Escort Carrier", AircraftCarrierEntity.class),
  SEAPLANE_CARRIER("Seaplane Carrier", AircraftCarrierEntity.class),
  BATTLESHIP("Battleship", CapitalShipEntity.class),
  BATTLECRUISER("Battlecruiser", CapitalShipEntity.class),
  HEAVY_CRUISER("Heavy Cruiser", CapitalShipEntity.class),
  LIGHT_CRUISER("Light Cruiser", CapitalShipEntity.class),
  ARMOURED_CRUISER("Armoured Cruiser", SurfaceShipEntity.class),
  DESTROYER("Destroyer", SurfaceShipEntity.class),
  DESTROYER_ESCORT("Destroyer Escort", SurfaceShipEntity.class),
  CORVETTE("Corvette", SurfaceShipEntity.class),
  MINE_LAYER("Mine Layer", SurfaceShipEntity.class),
  MINE_SWEEPER("Mine Sweeper", SurfaceShipEntity.class),
  SLOOP("Sloop", SurfaceShipEntity.class),
  OILER("Oiler", SurfaceShipEntity.class),
  TRANSPORT("Transport", SurfaceShipEntity.class),
  ANTI_AIRCRAFT("Anti-Aircraft", SurfaceShipEntity.class),
  FLAK_SHIP("Flak Ship", SurfaceShipEntity.class),
  MTB("MTB", SurfaceShipEntity.class),
  SUBMARINE("Submarine", SubmarineEntity.class),
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
