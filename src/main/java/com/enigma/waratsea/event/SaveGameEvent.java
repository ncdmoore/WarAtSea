package com.enigma.waratsea.event;

import lombok.Data;

@Data
public class SaveGameEvent implements Event {
  String name = "default";
}
