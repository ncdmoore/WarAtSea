package com.enigma.waratsea.model.ship;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum GunType {
  PRIMARY("Primary"),
  SECONDARY("Secondary"),
  TERTIARY("Tertiary"),
  ANTI_AIR("Anti-air");

  private final String value;

  public static Stream<GunType> stream() {
    return Stream.of(GunType.values());
  }
}
