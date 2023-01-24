package com.enigma.waratsea.entity.squadron;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AircraftAllotmentEntity {
  private Id id;
  private int number;
}
