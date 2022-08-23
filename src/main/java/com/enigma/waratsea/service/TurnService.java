package com.enigma.waratsea.service;

import com.enigma.waratsea.model.TurnIndex;
import com.enigma.waratsea.model.TurnType;
import com.google.inject.Singleton;

import java.util.Map;

import static com.enigma.waratsea.model.TurnIndex.*;
import static com.enigma.waratsea.model.TurnType.DAY;
import static com.enigma.waratsea.model.TurnType.NIGHT;

@Singleton
public class TurnService {
  private final Map<TurnIndex, TurnIndex> nextTurn = Map.of(
      DAY_1, DAY_2,        // DAY_1's next index is DAY_2, etc.
      DAY_2, DAY_3,
      DAY_3, TWILIGHT,
      TWILIGHT, NIGHT_1,
      NIGHT_1, NIGHT_2,
      NIGHT_2, DAY_1
  );

  public TurnIndex nextIndex(final TurnIndex currentTurnIndex) {
    return nextTurn.get(currentTurnIndex);
  }

  public TurnType getTrue(final TurnType turnType) {
    switch (turnType) {
      case DAY:
      case NIGHT:
        return turnType;
      case TWILIGHT:
        return NIGHT; // TODO fix this.
      default:
        return DAY;   // TODO fix this.
    }
  }
}
