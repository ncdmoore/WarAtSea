package com.enigma.waratsea.model;

import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
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

  @Builder.Default
  private Set<Nation> nations = new HashSet<>();

  public Airfield addNation(final Nation nation) {
    nations.add(nation);

    return this;
  }

  @Override
  public void deploySquadron(final Squadron squadron) {
    squadrons.add(squadron);
    squadron.setDeploymentState(AT_AIRFIELD);
    squadron.setState(READY);
    squadron.setAirbase(this);
  }

  @Override
  public boolean isOperational() {
    return capacity > 0;
  }
}
