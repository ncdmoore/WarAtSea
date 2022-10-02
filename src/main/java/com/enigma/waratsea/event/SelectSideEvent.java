package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Side;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SelectSideEvent implements Event {
  private final Side side;
}
