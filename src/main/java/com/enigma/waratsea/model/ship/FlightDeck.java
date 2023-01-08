package com.enigma.waratsea.model.ship;

import lombok.Data;

import java.util.List;

@Data
public class FlightDeck {
  private ArmourType armour;
  private List<Integer> capacity;

  // The current flight deck health. The health is an index into the capacity list
  private int health;
}
