package com.enigma.waratsea.model.statistics;

import com.enigma.waratsea.model.aircraft.Attack;
import lombok.Builder;

@Builder
public class SuccessRateVisitor implements ProbabilityVisitor {
  private static final int SIX_SIDED_DIE = 6;
  private static final int PERCENTAGE = 100;

  public int visit(final Attack attack) {
    return calculateChanceOfAtLeastOneHit(attack);
  }

  private int calculateChanceOfAtLeastOneHit(final Attack attack) {
    var atLeastOneHit = 1.0 - probabilityOfZeroHits(attack);

    return (int) (atLeastOneHit * PERCENTAGE);
  }

  private double probabilityOfZeroHits(final Attack attack) {
    var numHits = attack.getModifier() + 1;
    var numberOfDieToRoll = attack.getFactor();

    var numerator = Math.pow((SIX_SIDED_DIE - numHits), numberOfDieToRoll);
    var denominator = Math.pow(SIX_SIDED_DIE, numberOfDieToRoll);

    var result = (numerator / denominator);

    result = Math.max(result, 0.0);
    result = Math.min(result, 1.0);

    return result;
  }
}
