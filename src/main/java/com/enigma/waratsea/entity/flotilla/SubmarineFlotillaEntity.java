package com.enigma.waratsea.entity.flotilla;

import com.enigma.waratsea.model.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SubmarineFlotillaEntity extends FlotillaEntity {
  private String id;
  private Set<Id> subs;
}
