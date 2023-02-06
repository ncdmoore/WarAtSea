package com.enigma.waratsea.entity.option;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.AllotmentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SquadronAllotmentModificationEntity {
  private final Id allotmentId;
  private final AllotmentType type;
  private final int dice;
  private final int factor;
}
