package com.enigma.waratsea.model.option;

import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.squadron.AllotmentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SquadronAllotmentModification {
  private final NationId allotmentId;
  private final AllotmentType type;
  private final int dice;
  private final int factor;
}
