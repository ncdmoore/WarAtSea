package com.enigma.waratsea.event;

public interface EventHandler<E extends Event> {
  void notify(E event);
}
