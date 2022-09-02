package com.enigma.waratsea.viewmodel.events;

import com.enigma.waratsea.event.Event;
import javafx.stage.Stage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NavigateEvent implements Event {
  private final NavigationType type;
  private final Class<?> clazz;
  private final Stage stage;
}
