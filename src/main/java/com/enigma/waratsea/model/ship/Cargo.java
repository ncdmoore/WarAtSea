package com.enigma.waratsea.model.ship;

import lombok.Data;

@Data
public class Cargo {
  private int capacity;
  private int level;
  private String originPort;
}
