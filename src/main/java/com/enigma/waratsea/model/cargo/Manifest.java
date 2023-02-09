package com.enigma.waratsea.model.cargo;

import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class Manifest {
  private String id;
  private String originPort;
  private Set<String> destinationPorts;
  private Set<Ship> ships;
}
