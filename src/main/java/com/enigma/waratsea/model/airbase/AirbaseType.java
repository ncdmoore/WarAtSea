package com.enigma.waratsea.model.airbase;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum AirbaseType {
  AIRFIELD(airbase -> airbase instanceof Airfield),
  SHIP(airbase -> airbase instanceof Ship);

  private final Predicate<Airbase> filter;

  public static Stream<AirbaseType> stream() {
    return Stream.of(AirbaseType.values());
  }
}
