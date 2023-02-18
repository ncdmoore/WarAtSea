package com.enigma.waratsea.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Id implements Comparable<Id> {
  private final Side side;
  private final String name;

  @Override
  public int compareTo(@NotNull final Id o) {
    return name.compareTo(o.name);
  }
}
