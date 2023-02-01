package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Die;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@Builder
public class Allotment {
  private Nation nation;
  private SquadronTypeAllotment bombers;
  private SquadronTypeAllotment fighters;
  private SquadronTypeAllotment recon;

  public List<Id> get(final Die die) {
    return Stream.of(bombers, fighters, recon)
        .flatMap(squadronType -> squadronType.get(die).stream())
        .toList();
  }
}
