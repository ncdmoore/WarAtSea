package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipRegistryEntity {
  private Id id;
  private String title;
  private Id shipClassId;
}
