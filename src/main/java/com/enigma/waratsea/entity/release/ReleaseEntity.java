package com.enigma.waratsea.entity.release;

import lombok.Getter;
import lombok.Setter;

public abstract class ReleaseEntity {
  @Getter
  @Setter
  private String type = "ReleaseEntity";

  public abstract String getId();
}
