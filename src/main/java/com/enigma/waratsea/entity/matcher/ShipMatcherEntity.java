package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShipMatcherEntity {
  private Set<ShipType> types;
  private Set<String> names;
  private Side side;
  private Nation nation;
}
