package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.dto.AllotmentModificationDto;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.die.Die;
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

  public void adjust(final AllotmentModificationDto dto) {
    var allotmentType = dto.getType();
    switch (allotmentType) {
      case BOMBER -> bombers.adjust(dto);
      case FIGHTER -> fighters.adjust(dto);
      case RECONNAISSANCE -> recon.adjust(dto);
    }
  }
}
