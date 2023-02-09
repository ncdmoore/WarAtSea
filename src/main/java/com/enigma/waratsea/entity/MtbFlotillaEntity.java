package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.AssetState;
import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MtbFlotillaEntity {
  private Id id;
  private String title;
  private AssetState state;
  private String location;
  private Set<Id> boats;
}
