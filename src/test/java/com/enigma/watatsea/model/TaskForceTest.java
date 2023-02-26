package com.enigma.watatsea.model;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.ship.AircraftCarrier;
import com.enigma.waratsea.model.ship.CapitalShip;
import com.enigma.waratsea.model.ship.Catapult;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.SurfaceShip;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.model.taskForce.TaskForce;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.aircraft.AircraftType.DIVE_BOMBER;
import static com.enigma.waratsea.model.aircraft.AircraftType.FIGHTER;
import static com.enigma.waratsea.model.aircraft.AircraftType.RECONNAISSANCE;
import static com.enigma.waratsea.model.ship.ShipType.AIRCRAFT_CARRIER;
import static com.enigma.waratsea.model.ship.ShipType.DESTROYER;
import static com.enigma.waratsea.model.ship.ShipType.LIGHT_CRUISER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskForceTest {
  @Test
  void shouldGetTaskForceAirbases() {
    var ships = Set.of(
        buildAircraftCarrier(),
        buildAircraftCarrier(),
        buildCapitalShip(),
        buildSurfaceShip()
    );

    var taskForce = TaskForce.builder()
        .ships(ships)
        .build();

    var result = taskForce.getAirbases();

    assertEquals(3, result.size());
  }

  @Test
  void shouldGetTaskForceShipSummary() {
    var ships = Set.of(
        buildAircraftCarrier(),
        buildAircraftCarrier(),
        buildCapitalShip(),
        buildSurfaceShip()
    );

    var taskForce = TaskForce.builder()
        .ships(ships)
        .build();

    var result = taskForce.getShipSummary();

    assertEquals(2, result.get(AIRCRAFT_CARRIER));
    assertEquals(1, result.get(LIGHT_CRUISER));
    assertEquals(1, result.get(DESTROYER));
  }

  @Test
  void shouldGetTaskForceSquadronSummary() {
    var ships = Set.of(
        buildAircraftCarrier(),
        buildAircraftCarrier(),
        buildCapitalShip(),
        buildCapitalShip(),
        buildSurfaceShip()
    );

    var taskForce = TaskForce.builder()
        .ships(ships)
        .build();

    var result = taskForce.getSquadronSummary();

    assertEquals(2, result.get(FIGHTER));
    assertEquals(4, result.get(DIVE_BOMBER));
    assertEquals(2, result.get(RECONNAISSANCE));
  }

  private Ship buildAircraftCarrier() {
    return AircraftCarrier.builder()
        .type(AIRCRAFT_CARRIER)
        .squadrons(buildSquadronsForAircraftCarrier())
        .build();
  }

  private Ship buildCapitalShip() {
    var catapult = Catapult.builder()
        .capacity(1)
        .maxHealth(1)
        .health(1)
        .build();

    return CapitalShip.builder()
        .type(LIGHT_CRUISER)
        .catapult(catapult)
        .squadrons(buildSquadronsForCapitalShip())
        .build();
  }

  private Ship buildSurfaceShip() {
    return SurfaceShip.builder()
        .type(DESTROYER)
        .build();
  }

  private Set<Squadron> buildSquadronsForAircraftCarrier() {
    var fighter = Aircraft.builder().id(new Id(ALLIES, "F1")).type(FIGHTER).build();
    var diveBomber1 = Aircraft.builder().id(new Id(ALLIES, "DB1")).type(DIVE_BOMBER).build();
    var diveBomber2 = Aircraft.builder().id(new Id(ALLIES, "DB2")).type(DIVE_BOMBER).build();

    return Set.of(Squadron.builder().id(new Id(ALLIES, "sc1")).aircraft(fighter).build(),
        Squadron.builder().id(new Id(ALLIES, "sc2")).aircraft(diveBomber1).build(),
        Squadron.builder().id(new Id(ALLIES, "sc3")).aircraft(diveBomber2).build()
    );
  }

  private Set<Squadron> buildSquadronsForCapitalShip() {
    var recon = Aircraft.builder().type(RECONNAISSANCE).build();

    return Set.of(Squadron.builder().id(new Id(ALLIES, "ss4")).aircraft(recon).build()
    );
  }
}
