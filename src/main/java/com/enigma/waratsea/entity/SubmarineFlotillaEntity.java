package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubmarineFlotillaEntity {
  private Id id;
  private Set<Id> subs;
}
