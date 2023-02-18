package com.enigma.waratsea.model;

import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.enigma.waratsea.model.AssetState.RESERVE;

@Getter
@Builder
public class SubmarineFlotilla implements Comparable<SubmarineFlotilla> {
  private Id id;
  private AssetState state;
  private Set<Ship> subs;

  public boolean isReserved() {
    return state == RESERVE;
  }

  @Override
  public String toString() {
    return id.getName();
  }

  @Override
  public int compareTo(@NotNull final SubmarineFlotilla o) {
    return id.compareTo(o.id);
  }
}
