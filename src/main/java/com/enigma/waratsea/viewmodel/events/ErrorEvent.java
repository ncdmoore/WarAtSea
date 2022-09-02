package com.enigma.waratsea.viewmodel.events;

import com.enigma.waratsea.event.Event;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorEvent implements Event {
  private final String message;

  @Builder.Default
  private final boolean fatal = false;
}
