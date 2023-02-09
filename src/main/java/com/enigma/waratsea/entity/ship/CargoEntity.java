package com.enigma.waratsea.entity.ship;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class CargoEntity {
  private int maxCapacity;
  private int capacity;
  private int level;
  private String originPort;

  @Builder.Default
  private Set<String> destinationPorts = Collections.emptySet();
}
