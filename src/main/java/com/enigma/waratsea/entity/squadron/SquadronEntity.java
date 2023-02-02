package com.enigma.waratsea.entity.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.DeploymentState;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import com.enigma.waratsea.model.squadron.SquadronState;
import com.enigma.waratsea.model.squadron.SquadronStrength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.enigma.waratsea.model.squadron.DeploymentState.NOT_DEPLOYED;
import static com.enigma.waratsea.model.squadron.SquadronConfiguration.NONE;
import static com.enigma.waratsea.model.squadron.SquadronState.CREATED;
import static com.enigma.waratsea.model.squadron.SquadronStrength.FULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SquadronEntity {
  private Id id;
  private Id aircraft;

  @Builder.Default
  private SquadronStrength strength = FULL;

  @Builder.Default
  private DeploymentState deploymentState = NOT_DEPLOYED;

  @Builder.Default
  private SquadronState state = CREATED;

  @Builder.Default
  private SquadronConfiguration configuration = NONE;
}
