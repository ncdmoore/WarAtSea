package com.enigma.waratsea.event.user;

import com.enigma.waratsea.event.Event;
import com.enigma.waratsea.model.Side;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SelectSideEvent implements Event {
  private final Side side;
}
