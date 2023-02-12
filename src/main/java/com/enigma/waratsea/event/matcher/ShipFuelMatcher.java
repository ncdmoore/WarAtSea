package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.event.action.FuelAction;
import com.enigma.waratsea.event.ship.ShipFuelEvent;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class ShipFuelMatcher {
  private ShipMatcher ship;

  @Builder.Default
  private Set<FuelAction> actions = Collections.emptySet();

  public boolean match(final ShipFuelEvent event) {
    var candidateShip = event.getShip();
    var candidateAction = event.getAction();

    return matchShip(candidateShip)
        && matchAction(candidateAction);
  }

  private boolean matchShip(final Ship candidateShip) {
    return ship == null || ship.match(candidateShip);
  }

  private boolean matchAction(final FuelAction candidateAction) {
    return actions.isEmpty() || actions.contains(candidateAction);
  }
}
