package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipFuelMatcher;
import com.enigma.waratsea.event.ship.ShipFuelEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder public class ShipOutOfFuelVictory implements Victory {
  private String id;
  private String description;
  private ShipFuelMatcher matcher;

  private int totalPoints;

  @Override
  public void registerEvents(final Events events) {
    events.getShipFuelEvent().register(this::handleShipEvent);
  }

  private void handleShipEvent(final ShipFuelEvent event) {
    if (matcher.match(event)) {
      var points = event.getShip().getVictoryPoints() / 2;
      totalPoints += points;
    }
  }
}
