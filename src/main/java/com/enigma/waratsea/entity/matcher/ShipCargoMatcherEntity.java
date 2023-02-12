package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.CargoAction;
import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShipCargoMatcherEntity {
  private ShipMatcherEntity ship;
  private Set<CargoAction> actions;
  private Id originPort;
  private Set<Id> destinationPorts;
}
