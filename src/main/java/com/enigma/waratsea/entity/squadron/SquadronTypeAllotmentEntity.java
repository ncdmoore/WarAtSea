package com.enigma.waratsea.entity.squadron;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SquadronTypeAllotmentEntity {
  private int dice;
  private int factor;
  List<GroupAllotmentEntity> groups;
}
