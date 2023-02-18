package com.enigma.waratsea.model.option;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Builder
public class AllotmentModification implements Comparable<AllotmentModification> {
  private int id;
  private String text;
  private List<SquadronAllotmentModification> modifications;

  @Override
  public int compareTo(@NotNull final AllotmentModification o) {
    if (id == o.id) {
      return 0;
    } else if (id < o.id) {
      return -1;
    }
    return 1;
  }
}
