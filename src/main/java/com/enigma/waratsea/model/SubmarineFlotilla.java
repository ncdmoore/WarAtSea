package com.enigma.waratsea.model;

import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class SubmarineFlotilla {
  private Id id;
  private Set<Ship> subs;
}
