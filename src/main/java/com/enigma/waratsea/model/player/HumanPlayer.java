package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.Airbase;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Port;
import com.enigma.waratsea.model.Side;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class HumanPlayer implements Player {
  private final Side side;

  private Set<Airfield> airfields;

  @Setter
  private Set<Port> ports;

  private final Set<Airbase> airbases = new HashSet<>();

  public void setAirfields(final Set<Airfield> airfields) {
    this.airfields = airfields;

    airbases.addAll(airfields);
  }
}
