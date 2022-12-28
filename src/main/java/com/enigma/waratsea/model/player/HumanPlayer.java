package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class HumanPlayer implements Player {
  private final Side side;

  private Set<Airfield> airfields;

  @Setter
  private Set<Port> ports;

  private final Map<Id, Airbase> airbases = new HashMap<>();

  public void setAirfields(final Set<Airfield> airfields) {
    this.airfields = airfields;

    airfields.forEach(this::addToAirbase);
  }

  private void addToAirbase(final Airbase airbase) {
    airbases.putIfAbsent(airbase.getId(), airbase);
  }
}
