package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.LandingType;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.*;
import lombok.Builder;
import lombok.Data;

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
  private AttackEntity navalWarship;
  private AttackEntity navalTransport;
  private AttackEntity land;
  private AttackEntity air;
  private PerformanceEntity performance;
  private Frame frame;
}
