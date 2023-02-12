package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.aircraft.AircraftType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class SquadronMatcher {
  @Builder.Default
  private Set<AircraftType> types = Collections.emptySet();

  @Builder.Default
  private Set<String> names = Collections.emptySet();

  private Side side;
  private Nation nation;

  public boolean match(final Squadron candidateSquadron) {
    return matchType(candidateSquadron)
        && matchName(candidateSquadron)
        && matchSide(candidateSquadron)
        && matchNation(candidateSquadron);
  }

  private boolean matchType(final Squadron candidateSquadron) {
    var aircraftType = candidateSquadron.getAircraft().getType();
    return types.isEmpty() || types.contains(aircraftType);
  }

  private boolean matchName(final Squadron candidateSquadron) {
    var squadronName = candidateSquadron.getId().getName();
    return names.isEmpty() || names.contains(squadronName);
  }

  private boolean matchSide(final Squadron candidateSquadron) {
    var squadronSide = candidateSquadron.getId().getSide();
    return side == null || side == squadronSide;
  }

  private boolean matchNation(final Squadron candidateSquadron) {
    var squadronNation = candidateSquadron.getAircraft().getNation();
    return nation == null || nation == squadronNation;
  }
}
