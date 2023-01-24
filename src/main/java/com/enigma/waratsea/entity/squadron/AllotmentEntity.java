package com.enigma.waratsea.entity.squadron;

import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllotmentEntity {
  private Nation nation;
  private SquadronTypeAllotmentEntity bombers;
  private SquadronTypeAllotmentEntity fighters;
  private SquadronTypeAllotmentEntity recon;
}
