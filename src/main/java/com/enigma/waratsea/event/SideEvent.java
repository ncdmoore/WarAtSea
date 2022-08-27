package com.enigma.waratsea.event;

import com.enigma.waratsea.model.Side;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Thrown when a side is selected.
 */
@Data
@RequiredArgsConstructor
public class SideEvent implements Event {
  private final Side side;
}
