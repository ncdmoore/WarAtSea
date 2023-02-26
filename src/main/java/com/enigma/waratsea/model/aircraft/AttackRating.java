package com.enigma.waratsea.model.aircraft;

import com.enigma.waratsea.model.squadron.SquadronStrength;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttackRating {
  // Determines which values of a six sided die are hits. For example a modifier of
  // 1 indicates that both a 5 and a 6 are hits. Note, a 6 is always a hit.
  private int modifier;

  // Number of dice to roll based on squadron strength.
  private int full;
  private int half;
  private int sixth;

  // Indicates if the factor is defensive only. Only returns fire does not initiate.
  private boolean defensive;

  // The final factor in determining a successful attack.
  private double finalModifier;

  public Attack getAttack(final SquadronStrength squadronStrength) {
    return Attack.builder()
        .modifier(modifier)
        .factor(getFactor(squadronStrength))
        .defensive(defensive)
        .finalModifier(finalModifier)
        .build();
  }

  public AttackRating zeroModifier() {
    return AttackRating.builder()
        .modifier(0)
        .full(full)
        .half(half)
        .sixth(sixth)
        .defensive(isDefensive())
        .finalModifier(finalModifier)
        .build();
  }

  public AttackRating zeroAll() {
    return AttackRating.builder()
        .modifier(0)
        .full(0)
        .half(0)
        .sixth(0)
        .defensive(isDefensive())
        .finalModifier(finalModifier)
        .build();
  }

  public AttackRating reduceRoundUp(final int reductionFactor) {
    return AttackRating.builder()
        .modifier(modifier)
        .full(roundUp(full, reductionFactor))
        .half(roundUp(half, reductionFactor))
        .sixth(roundUp(sixth, reductionFactor))
        .defensive(defensive)
        .finalModifier(finalModifier)
        .build();
  }

  public AttackRating reduceRoundDown(final int reductionFactor) {
    return AttackRating.builder()
        .modifier(modifier)
        .full(roundDown(full, reductionFactor))
        .half(roundDown(half, reductionFactor))
        .sixth(roundDown(sixth, reductionFactor))
        .defensive(defensive)
        .finalModifier(finalModifier)
        .build();
  }

  private int roundUp(final int factor, final int reductionFactor) {
    return (factor / reductionFactor) + (factor % reductionFactor);
  }

  private int roundDown(final int factor, final int reductionFactor) {
    return factor / reductionFactor;
  }

  private int getFactor(final SquadronStrength squadronStrength) {
    return switch (squadronStrength) {
      case FULL -> full;
      case HALF -> half;
      case SIXTH -> sixth;
    };
  }
}
