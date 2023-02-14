package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ShipSunkVictory implements Victory {
  private String id;
  private String description;
  private ShipCombatMatcher matcher;

  private int totalPoints;

  @Override
  public void handleEvent(VictoryDto victoryDto) {
    var shipCombatEvent = victoryDto.getShipCombatEvent();

    Optional.ofNullable(shipCombatEvent)
        .ifPresent(this::handleShipEvent);
  }

  private void handleShipEvent(final ShipCombatEvent event) {
    if (matcher.match(event)) {
      var points = event.getShip().getVictoryPoints();
      totalPoints += points;
    }
  }
}
