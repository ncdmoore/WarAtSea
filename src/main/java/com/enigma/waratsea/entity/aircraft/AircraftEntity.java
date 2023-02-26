package com.enigma.waratsea.entity.aircraft;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.aircraft.AltitudeType;
import com.enigma.waratsea.model.aircraft.Frame;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.aircraft.ServiceType;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AircraftEntity {
  private Id id;
  private String title;
  private AircraftType type;
  private Nation nation;
  private ServiceType service;
  private String designation;
  private AltitudeType altitude;
  private LandingType takeOff;
  private LandingType landing;
  private AttackRatingEntity navalWarship;
  private AttackRatingEntity navalTransport;
  private AttackRatingEntity land;
  private AttackRatingEntity air;
  private PerformanceEntity performance;
  private Frame frame;
  private Set<SquadronConfiguration> configuration;
}
