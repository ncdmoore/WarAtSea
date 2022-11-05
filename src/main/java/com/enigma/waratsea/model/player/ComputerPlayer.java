package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Port;
import com.enigma.waratsea.model.Side;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ComputerPlayer implements Player {
  private final Side side;

  @Setter
  private Set<Airfield> airfields;

  @Setter
  private Set<Port> ports;
}
