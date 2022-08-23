package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.TurnType;
import com.enigma.waratsea.model.VisibilityType;
import com.google.inject.Singleton;

import java.util.Map;

import static com.enigma.waratsea.model.TurnType.*;
import static com.enigma.waratsea.model.VisibilityType.DAY;
import static com.enigma.waratsea.model.VisibilityType.NIGHT;

@Singleton
public class TurnService {
  private final Map<TurnType, TurnType> nextTurnType = Map.of(
      DAY_1, DAY_2,        // DAY_1's next index is DAY_2, etc.
      DAY_2, DAY_3,
      DAY_3, TWILIGHT,
      TWILIGHT, NIGHT_1,
      NIGHT_1, NIGHT_2,
      NIGHT_2, DAY_1
  );

  public Turn next(final Turn currentTurn) {
    var currentTurnType = currentTurn.getTurnType();

    var turnType = nextType(currentTurnType);
    var number = currentTurn.getNumber() + 1;
    var visibilityType = getVisibilityType(turnType);

    return Turn.builder()
        .turnType(turnType)
        .visibilityType(visibilityType)
        .number(number)
        .build();
  }

  private TurnType nextType(final TurnType currentTurnType) {
    return nextTurnType.get(currentTurnType);
  }


  private VisibilityType getVisibilityType(final TurnType turnType) {
    switch (turnType) {
      case DAY_1:
      case DAY_2:
      case DAY_3:
        return DAY;
      case NIGHT_1:
      case NIGHT_2:
        return NIGHT;
      case TWILIGHT:
        return NIGHT; // TODO fix this.
      default:
        return DAY;   // TODO fix this.
    }
  }
}
