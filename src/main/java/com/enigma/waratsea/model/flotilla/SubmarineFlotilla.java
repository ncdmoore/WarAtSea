package com.enigma.waratsea.model.flotilla;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.Submarine;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class SubmarineFlotilla implements Flotilla {
  private Id id;
  private Set<Submarine> subs;
}
