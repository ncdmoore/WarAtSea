package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.ship.ShipFuelEvent;
import com.enigma.waratsea.event.matcher.ShipFuelMatcher;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder public class ShipOutOfFuelVictory implements Victory {
  private String id;
  private String description;
  private ShipFuelMatcher matcher;

  private int totalPoints;

  @Override
  public void handleEvent(VictoryDto victoryDto) {
    var shipFuelEvent = victoryDto.getShipFuelEvent();

    Optional.ofNullable(shipFuelEvent)
        .ifPresent(this::handleShipEvent);
  }

  private void handleShipEvent(final ShipFuelEvent event) {
    if (matcher.match(event)) {
      var points = event.getShip().getVictoryPoints() / 2;
      totalPoints += points;
    }
  }
}
