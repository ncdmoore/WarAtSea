package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class ShipMatcher {
  @Builder.Default
  private Set<ShipType> types = Collections.emptySet();

  @Builder.Default
  private Set<String> names = Collections.emptySet();

  private Side side;
  private Nation nation;

  public boolean match(final Ship candidateShip) {
    return matchType(candidateShip)
        && matchName(candidateShip)
        && matchSide(candidateShip)
        && matchNation(candidateShip);
  }

  private boolean matchType(final Ship candidateShip) {
    var shipType = candidateShip.getType();
    return types.isEmpty() || types.contains(shipType);
  }

  private boolean matchName(final Ship candidateShip) {
    var shipName = candidateShip.getId().getName();
    return names.isEmpty() || names.contains(shipName);
  }

  private boolean matchSide(final Ship candidateShip) {
    var shipSide = candidateShip.getId().getSide();
    return side == null || side == shipSide;
  }

  private boolean matchNation(final Ship candidateShip) {
    var shipNation = candidateShip.getNation();
    return nation == null || nation == shipNation;
  }
}
