package com.enigma.waratsea.model.release;

import com.enigma.waratsea.event.Events;

public interface Release {
  void registerEvents(Events events);
}
