package com.enigma.waratsea.model;

import com.enigma.waratsea.model.ship.Submarine;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class SubmarineFlotilla {
  private Id id;
  private Set<Submarine> subs;
}
