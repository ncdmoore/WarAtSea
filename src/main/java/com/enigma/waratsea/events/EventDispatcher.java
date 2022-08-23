package com.enigma.waratsea.events;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for delivering events to event handlers.
 *
 * @param <E> The type of event.
 */
@Slf4j
public class EventDispatcher<E extends Event> {
  private final Set<EventHandler<E>> handlers = new HashSet<>();
  private final String name;

  public EventDispatcher(final String name) {
    this.name = name;
  }

  public void register(final EventHandler<E> handler) {
    log.debug("Event {}: registers handler for: {}", name, handler);
    handlers.add(handler);
  }

  public void unregister(final EventHandler<E> handler) {
    log.debug("Event {}: unregisters handler for: {}", name, handler);
    handlers.remove(handler);
  }

  public void fire(final E e) {
    log.debug("Fire event: '{}'", e);

    // We need to copy the handler list because a handler may unregister while doing the actual event processing.
    // This is a very common pattern. A handler receives the event, processes the event, and now is no longer
    // interested in the event. Thus, it unregisters to keep from receiving unwanted events. If we don't copy
    // the handlers we may end up with the map's keys being modified while we are trying to iterate over them.
    // This can lead to a memory corruption exception.
    Set<EventHandler<E>> copyOfHandlers = new HashSet<>(handlers);

    copyOfHandlers.forEach(h -> h.notify(e));
  }
}
