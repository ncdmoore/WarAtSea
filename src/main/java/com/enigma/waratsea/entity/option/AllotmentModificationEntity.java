package com.enigma.waratsea.entity.option;

import com.enigma.waratsea.model.option.OptionId;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllotmentModificationEntity {
  private OptionId id;
  private String text;
  private List<SquadronAllotmentModificationEntity> modifications;
}
