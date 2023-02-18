package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCargoMatcher;
import com.enigma.waratsea.event.ship.ShipCargoEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipCargoUnloadedVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private int factor;
  private ShipCargoMatcher matcher;

  private int totalPoints;

  @Override
  public void registerEvents(final Events events) {
    events.getShipCargoEvent().register(this::handleShipCargoEvent);
  }

  private void handleShipCargoEvent(final ShipCargoEvent event) {
    if (matcher.match(event)) {

      if (points > 0) {
        handleBasicPointsAward();
      }

      if (factor > 0) {
        handleFactorPointsAward(event);
      }
    }
  }

  private void handleBasicPointsAward() {
    totalPoints += points;
  }

  private void handleFactorPointsAward(final ShipCargoEvent event) {
    var cargoLevel = event.getCargoLevel();
    totalPoints += factor * cargoLevel;
  }
}
