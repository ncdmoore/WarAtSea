package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class Commission {
  private Id id;
  private String title;
  private Nation nation;
  private Set<Squadron> squadrons;
}
