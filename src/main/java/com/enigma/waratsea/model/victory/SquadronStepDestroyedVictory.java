package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;
import com.enigma.waratsea.event.matcher.SquadronCombatMatcher;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class SquadronStepDestroyedVictory implements Victory {
  private String id;
  private String description;
  private int points;
  private SquadronCombatMatcher matcher;

  private int totalPoints;

  @Override
  public void handleEvent(VictoryDto victoryDto) {
    var squadronCombatEvent = victoryDto.getSquadronCombatEvent();

    Optional.ofNullable(squadronCombatEvent)
        .ifPresent(this::handleSquadronEvent);
  }

  public void handleSquadronEvent(final SquadronCombatEvent event) {
    if (matcher.match(event)) {
      totalPoints += points;
    }
  }
}
