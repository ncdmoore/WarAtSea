package com.enigma.waratsea.entity.release;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipAttackedRelease extends ReleaseEntity {

  public ShipAttackedRelease() {
    type = "ShipAttackedRelease";
  }
}
