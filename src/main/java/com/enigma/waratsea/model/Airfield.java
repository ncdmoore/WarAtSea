package com.enigma.waratsea.model;

import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

import static com.enigma.waratsea.model.squadron.DeploymentState.AT_AIRFIELD;
import static com.enigma.waratsea.model.squadron.SquadronState.READY;

@Getter
@Builder
public class Airfield implements Airbase {
  private Id id;
  private String title;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int capacity;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
  private Set<Squadron> squadrons;

  @Override
  public void deploySquadron(Squadron squadron) {
    squadrons.add(squadron);
    squadron.setDeploymentState(AT_AIRFIELD);
    squadron.setState(READY);
  }
}
