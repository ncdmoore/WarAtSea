package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.ShipAction;
import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShipCargoMatcherEntity {
  private ShipMatcherEntity ship;
  private Set<ShipAction> actions;
  private Set<Id> originPorts;
  private Set<Id> destinationPorts;
}
