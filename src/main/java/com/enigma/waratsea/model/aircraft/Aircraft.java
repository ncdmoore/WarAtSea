package com.enigma.waratsea.model.aircraft;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.LandingType;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Data;

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
  private Attack navalWarship;
  private Attack navalTransport;
  private Attack land;
  private Attack air;
  private Performance performance;
  private Frame frame;
}
