package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.ship.Cargo;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class ShipCargoLostVictory implements Victory {
  private String id;
  private String description;
  private ShipCombatMatcher matcher;
  private int factor;

  private int totalPoints;

  @Override
  public void handleEvent(VictoryDto victoryDto) {
    var shipCombatEvent = victoryDto.getShipCombatEvent();

    Optional.ofNullable(shipCombatEvent)
        .ifPresent(this::handleShipEvent);
  }

  private void handleShipEvent(final ShipCombatEvent event) {
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
