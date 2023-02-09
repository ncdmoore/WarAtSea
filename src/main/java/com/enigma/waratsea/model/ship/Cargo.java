package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.dto.CargoDto;
import lombok.Data;

import java.util.Set;

@Data
public class Cargo {
  private int maxCapacity;
  private int capacity;
  private int level;
  private String originPort;
  private Set<String> destinationPorts;

  public void load(final CargoDto cargoDto) {
    level = capacity;
    originPort = cargoDto.originPort();
    destinationPorts = cargoDto.destinationPorts();
  }
}
