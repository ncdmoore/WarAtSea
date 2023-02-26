package com.enigma.waratsea.model.aircraft;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Aircraft {
  private Id id;
  private String title;
  private AircraftType type;
  private Nation nation;
  private ServiceType service;
  private String designation;
  private AltitudeType altitude;
  private LandingType takeOff;
  private LandingType landing;
  private AttackRating navalWarship;
  private AttackRating navalTransport;
  private AttackRating land;
  private AttackRating air;
  private Performance performance;
  private Frame frame;
  private Set<SquadronConfiguration> configuration;
}
