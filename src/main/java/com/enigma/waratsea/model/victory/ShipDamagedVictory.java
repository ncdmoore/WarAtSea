package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipDamagedVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private ShipCombatMatcher matcher;

  private int totalPoints;

  public void handleShipEvent(final ShipCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }
}
