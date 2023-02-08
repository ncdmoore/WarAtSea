package com.enigma.waratsea.entity.squadron;

import com.enigma.waratsea.model.NationId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllotmentEntity {
  private NationId id;
  private SquadronTypeAllotmentEntity bombers;
  private SquadronTypeAllotmentEntity fighters;
  private SquadronTypeAllotmentEntity recon;
}
