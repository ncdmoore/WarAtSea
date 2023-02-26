package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.aircraft.Attack;
import com.enigma.waratsea.model.aircraft.AttackType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Squadron implements Comparable<Squadron> {
  private final Id id;
  private String title;
  private final Aircraft aircraft;
  private final SquadronStrength strength;

  @Setter
  private DeploymentState deploymentState;

  @Setter
  private SquadronState state;

  private SquadronConfiguration configuration;

  @Setter
  private Airbase airbase;

  @Builder
  public Squadron(final Id id,
                  final Aircraft aircraft,
                  final SquadronStrength strength,
                  final DeploymentState deploymentState,
                  final SquadronState state,
                  final SquadronConfiguration configuration) {
    this.id = id;
    this.aircraft = aircraft;
    this.strength = strength;
    this.deploymentState = deploymentState;
    this.state = state;
    this.configuration = configuration;

    setTitle();
  }

  @Override
  public String toString() {
    return title;
  }

  public boolean ofNation(final Nation nation) {
    return aircraft.getNation() == nation;
  }

  public Attack getAttack(final AttackType attackType) {
    return switch (attackType) {
      case AIR -> configuration.getAirAttackRating(aircraft).getAttack(strength);
      case LAND -> configuration.getLandAttackRating(aircraft).getAttack(strength);
      case NAVAL_TRANSPORT -> configuration.getNavalTransportAttackRating(aircraft).getAttack(strength);
      case NAVAL_WARSHIP -> configuration.getNavalWarshipAttackRating(aircraft).getAttack(strength);
    };
  }

  public int getRange() {
    return configuration.getRange(aircraft);
  }

  public int getEndurance() {
    return configuration.getEndurance(aircraft);
  }

  public int getFerryDistance() {
    return configuration.getFerryDistance(aircraft);
  }

  public int getRadius() {
    return configuration.getRadius(aircraft);
  }

  public Squadron setConfiguration(final SquadronConfiguration newConfiguration) {
    this.configuration = newConfiguration;
    return this;
  }

  @Override
  public int compareTo(final @NotNull Squadron o) {
    return title.compareTo(o.title);
  }

  private void setTitle() {
    var designation = aircraft.getDesignation();
    var currentName = id.getName();
    var index = currentName.indexOf("-");
    title = currentName.substring(0, index + 1) + designation + currentName.substring(index + 1);
  }
}
