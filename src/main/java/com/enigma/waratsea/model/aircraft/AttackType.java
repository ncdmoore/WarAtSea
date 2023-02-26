package com.enigma.waratsea.model.aircraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AttackType {
  AIR("Air Attack"),
  LAND("Land Attack"),
  NAVAL_WARSHIP("Naval Warship Attack"),
  NAVAL_TRANSPORT("Naval Transport Attack");

  private final String value;

  public static Stream<AttackType> stream() {
    return Stream.of(AttackType.values());
  }
}
