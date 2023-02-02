package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Squadron {
  private Id id;
  private Aircraft aircraft;
  private SquadronStrength strength;

  @Setter
  private DeploymentState deploymentState;

  @Setter
  private SquadronState state;

  private SquadronConfiguration configuration;
}
