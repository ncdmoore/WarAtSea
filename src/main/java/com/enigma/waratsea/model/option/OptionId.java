package com.enigma.waratsea.model.option;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class OptionId implements Comparable<OptionId> {
  private final OptionType type;
  private final String name;

  @Override
  public int compareTo(@NotNull OptionId o) {
    return name.compareTo(o.name);
  }
}
