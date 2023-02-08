package com.enigma.waratsea.entity.option;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllotmentModificationEntity {
  private int id;
  private String text;
  private List<SquadronAllotmentModificationEntity> modifications;
}
