package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.AssetState;
import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

import static com.enigma.waratsea.model.AssetState.ACTIVE;

@Data
@Builder
public class MtbFlotillaEntity {
  private Id id;
  private String title;
  private String location;

  @Builder.Default
  private AssetState state = ACTIVE;

  @Builder.Default
  private Set<Id> boats = Collections.emptySet();

}
