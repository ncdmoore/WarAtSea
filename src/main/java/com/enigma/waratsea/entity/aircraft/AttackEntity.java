package com.enigma.waratsea.entity.aircraft;

import com.enigma.waratsea.model.squadron.SquadronStrength;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttackEntity {
  // Determines which values of a six sided die are hits. For example a modifier of
  // 1 indicates that both a 5 and a 6 are hits. Note, a 6 is always a hit.
  private int modifier;

  // Number of dice to roll based on squadron strength.
  private Map<SquadronStrength, Integer> factor;

  // Indicates if the factor is defensive only. Only returns fire does not initiate.
  private boolean defensive;

  // The final factor in determining a successful attack.
  @Builder.Default
  private double finalModifier = 1.0;
}
