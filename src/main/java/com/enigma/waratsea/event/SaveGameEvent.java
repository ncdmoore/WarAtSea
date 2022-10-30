package com.enigma.waratsea.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SaveGameEvent implements Event {
  private final String scenarioName;
  private String name = "default";

  public String getId() {
    return String.join("-", scenarioName, name);
  }
}
