package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.entity.ship.AircraftCarrierEntity;
import com.enigma.waratsea.entity.ship.ShipEntity;
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
  DESTROYER("Destroyer", AircraftCarrierEntity.class),
  DESTROYER_ESCORT("Destroyer Escort", AircraftCarrierEntity.class),
  FLAK_SHIP("Flak Ship", AircraftCarrierEntity.class),
  MINE_LAYER("Mine Layer", AircraftCarrierEntity.class),
  MINE_SWEEPER("Mine Sweeper", AircraftCarrierEntity.class),
  MTB("MTB", AircraftCarrierEntity.class),
  OILER("Oiler", AircraftCarrierEntity.class),
  SLOOP("Sloop", AircraftCarrierEntity.class),
  TRANSPORT("Transport", AircraftCarrierEntity.class),
  SUBMARINE("Submarine", AircraftCarrierEntity.class),
  VIRTUAL("Virtual", AircraftCarrierEntity.class);

  private final String value;

  private final Class<? extends ShipEntity> clazz;

  public String toLower() {
    return value.replace(' ', '_').toLowerCase(Locale.ROOT);
  }

  public static Stream<ShipType> stream() {
    return Stream.of(ShipType.values());
  }
}
