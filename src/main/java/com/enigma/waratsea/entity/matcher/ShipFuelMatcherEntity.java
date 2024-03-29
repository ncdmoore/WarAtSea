package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.event.action.ShipAction;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShipFuelMatcherEntity {
  private ShipMatcherEntity ship;
  private Set<ShipAction> actions;
}
