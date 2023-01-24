package com.enigma.waratsea.entity.squadron;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupAllotmentEntity {
  private int priority;
  private int selectSize;
  private List<AircraftAllotmentEntity> aircraft;
}
