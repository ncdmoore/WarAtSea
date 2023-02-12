package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.port.Port;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@Builder
public class BaseMatcher {
  @Builder.Default
  private Set<String> names = Collections.emptySet();

  private Side side;

  public boolean match(final Airfield candidateAirfield) {
    var candidateName = candidateAirfield.getId().getName();
    var candidateSide = candidateAirfield.getId().getSide();

    return matchName(candidateName)
        && matchSide(candidateSide);
  }

  public boolean match(final Port candidatePort) {
    var candidateName = candidatePort.getId().getName();
    var candidateSide = candidatePort.getId().getSide();

    return matchName(candidateName)
        && matchSide(candidateSide);
  }

  private boolean matchName(final String candidateName) {
    return names.isEmpty() || names.contains(candidateName);
  }

  private boolean matchSide(final Side candidateSide) {
    return side == null || side == candidateSide;
  }
}
