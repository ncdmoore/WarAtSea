package com.enigma.watatsea.events;

import com.enigma.waratsea.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Strictly a class used in the aid of testing the event dispatcher.
 */
@Data
@AllArgsConstructor
public class TestEvent implements Event {
  private int count;
}
