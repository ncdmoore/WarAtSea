package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftCarrierEntity implements ShipEntity {
  private Id id;
  private Id shipClassId;
  private ShipType type;
  private String title;
  private String shipClass;
  private Nation nation;
  private FlightDeckEntity flightDeck;

  @Builder.Default
  private GunEntity secondary = GunEntity.builder().build();

  @Builder.Default
  private GunEntity tertiary = GunEntity.builder().build();

  @Builder.Default
  private GunEntity antiAir = GunEntity.builder().build();

  private TorpedoEntity torpedo;
  private HullEntity hull;
  private FuelEntity fuel;
  private MovementEntity movement;
  private Set<LandingType> landingType;
  private int victoryPoints;

  @Builder.Default
  private Set<Id> squadrons = Collections.emptySet();
}
