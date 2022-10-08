package com.enigma.waratsea.model.player;

import com.enigma.waratsea.model.Side;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HumanPlayer implements Player {
  @Getter
  private final Side side;
}
