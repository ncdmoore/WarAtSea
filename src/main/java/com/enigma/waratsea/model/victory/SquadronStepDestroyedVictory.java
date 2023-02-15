package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.SquadronCombatMatcher;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SquadronStepDestroyedVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private SquadronCombatMatcher matcher;

  private int totalPoints;

  @Override
  public void registerEvents(final Events events) {
    events.getSquadronCombatEvent().register(this::handleSquadronEvent);
  }

  public void handleSquadronEvent(final SquadronCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }
}
