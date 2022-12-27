package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import com.enigma.waratsea.model.squadron.SquadronState;
import com.enigma.waratsea.model.squadron.SquadronStrength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.enigma.waratsea.model.squadron.SquadronConfiguration.NONE;
import static com.enigma.waratsea.model.squadron.SquadronState.READY;
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
  private SquadronState state = READY;

  @Builder.Default
  private SquadronConfiguration configuration = NONE;
}
