package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.ship.Cargo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipCargoLostVictory implements Victory {
  private String id;
  private String description;
  private ShipCombatMatcher matcher;
  private int factor;

  private int totalPoints;

  public void handleShipEvent(final ShipCombatEvent event) {
    if (matcher.match(event)) {
      var ship = event.getShip();
      var cargoLevel = ship.retrieveCargo()
          .map(Cargo::getLevel)
          .orElse(0);

      var points = cargoLevel * factor;
      totalPoints += points;
    }
  }
}
