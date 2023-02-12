package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.event.action.CombatAction;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class SquadronCombatMatcher {
  private SquadronMatcher squadron;

  @Builder.Default
  private Set<CombatAction> actions = Collections.emptySet();

  public boolean match(final SquadronCombatEvent event) {
    var candidateSquadron = event.getSquadron();
    var candidateAction = event.getAction();

    return matchSquadron(candidateSquadron)
        && matchAction(candidateAction);
  }

  private boolean matchSquadron(final Squadron candidateSquadron) {
    return squadron == null || squadron.match(candidateSquadron);
  }

  private boolean matchAction(final CombatAction candidateAction) {
    return actions.isEmpty() || actions.contains(candidateAction);
  }
}
