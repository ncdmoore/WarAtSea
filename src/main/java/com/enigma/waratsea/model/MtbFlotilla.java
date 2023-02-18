package com.enigma.waratsea.model;

import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static com.enigma.waratsea.model.AssetState.RESERVE;

@Getter
@Builder
public class MtbFlotilla implements Comparable<MtbFlotilla> {
  private Id id;
  private String title;
  private AssetState state;
  private String location;
  private Set<Ship> boats;

  public boolean isReserved() {
    return state == RESERVE;
  }

  @Override
  public String toString() {
    return id.getName();
  }

  @Override
  public int compareTo(@NotNull final MtbFlotilla o) {
    return id.compareTo(o.id);
  }
}
