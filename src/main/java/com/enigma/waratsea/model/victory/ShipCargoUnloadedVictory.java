package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.ShipCargoMatcher;
import com.enigma.waratsea.event.ship.ShipCargoEvent;
import com.enigma.waratsea.model.ship.Cargo;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

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
  public void handleEvent(VictoryDto victoryDto) {
    var shipCargoEvent = victoryDto.getShipCargoEvent();

    Optional.ofNullable(shipCargoEvent)
        .ifPresent(this::handleShipCargoEvent);
  }

  public void handleShipCargoEvent(final ShipCargoEvent event) {
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
    var ship = event.getShip();
    var cargoLevel = ship.retrieveCargo().map(Cargo::getLevel).orElse(0);
    var points = factor * cargoLevel;
    totalPoints += points;
  }
}
