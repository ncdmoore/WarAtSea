package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SquadronEntity {
  private Id id;
  private Id aircraft;
}
