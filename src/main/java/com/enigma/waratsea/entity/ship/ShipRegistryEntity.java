package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

@Data
@Builder
public class ShipRegistryEntity {
  private Id id;
  private String title;
  private Id shipClassId;

  @Builder.Default
  private Set<Id> squadrons = Collections.emptySet();
}
