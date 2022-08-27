package com.enigma.waratsea.event;

/**
 * Implement this interface to receive game events.
 *
 * @param <E> The type of game event to receive.
 */
public interface EventHandler<E extends Event> {
  void notify(E event);
}
