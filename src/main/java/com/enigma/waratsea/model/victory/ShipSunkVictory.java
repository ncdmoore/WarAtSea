package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipSunkVictory implements Victory {
  private String id;
  private String description;
  private ShipCombatMatcher matcher;

  private int totalPoints;

  public void handleShipEvent(final ShipCombatEvent event) {
    if (matcher.match(event)) {
      var points = event.getShip().getVictoryPoints();
      totalPoints += points;
    }
  }
}
