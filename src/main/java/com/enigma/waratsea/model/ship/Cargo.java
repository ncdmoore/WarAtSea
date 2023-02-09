package com.enigma.waratsea.model.ship;

import lombok.Data;

import java.util.Set;

@Data
public class Cargo {
  private int maxCapacity;
  private int capacity;
  private int level;
  private String originPort;
  private Set<String> destinationPorts;
}
