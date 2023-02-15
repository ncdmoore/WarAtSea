package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.Events;
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
  private int requiredOccurrences;
  private BaseCombatMatcher matcher;


  private int totalPoints;
  private int totalOccurrences;

  @Override
  public void registerEvents(final Events events) {
    events.getAirfieldCombatEvent().register(this::handleAirfieldEvent);
    events.getPortCombatEvent().register(this::handlePortEvent);
  }

  private void handleAirfieldEvent(final AirfieldCombatEvent event) {
    totalOccurrences++;

    if (matcher.match(event) && occurrencesSatisfied()) {
      totalPoints += points;
    }
  }

  private void handlePortEvent(final PortCombatEvent event) {
    totalOccurrences++;

    if (matcher.match(event) && occurrencesSatisfied()) {
      totalPoints += points;
    }
  }

  private boolean occurrencesSatisfied() {
    return requiredOccurrences == 0 || totalOccurrences % requiredOccurrences == 0;
  }
}
