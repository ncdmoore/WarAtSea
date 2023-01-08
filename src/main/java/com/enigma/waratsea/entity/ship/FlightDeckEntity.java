package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.ship.ArmourType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightDeckEntity {
  private ArmourType armour;
  private List<Integer> capacity;

  // The current flight deck health. The health is an index into the capacity list
  private int health;
}
