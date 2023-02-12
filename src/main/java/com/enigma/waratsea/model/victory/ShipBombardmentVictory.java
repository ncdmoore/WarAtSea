package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.airfield.AirfieldCombatEvent;
import com.enigma.waratsea.event.matcher.BaseCombatMatcher;
import com.enigma.waratsea.event.port.PortCombatEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipBombardmentVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private BaseCombatMatcher matcher;

  private int totalPoints;

  public void handleAirfieldEvent(final AirfieldCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }

  public void handlePortEvent(final PortCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }
}
