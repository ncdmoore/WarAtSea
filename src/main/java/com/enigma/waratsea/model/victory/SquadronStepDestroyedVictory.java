package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.matcher.SquadronCombatMatcher;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadronStepDestroyedVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private SquadronCombatMatcher matcher;

  private int totalPoints;

  public void handleSquadronEvent(final SquadronCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }
}
