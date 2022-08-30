package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Side;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SideEvent implements Event {
  private final Side side;
}
