package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Side {
  ALLIES("Allies", "Allied"),
  AXIS("Axis", "Axis"),
  NEUTRAL("Neutral", "Neutral");

  private final String value;
  private final String possessive;

  private static final Map<Side, Side> OPPOSITE_MAP = Map.of(
      ALLIES, AXIS,
      AXIS, ALLIES,
      NEUTRAL, NEUTRAL);

  private static final Map<Side, List<Side>> FRIENDLY_MAP = Map.of(
      ALLIES, List.of(ALLIES, NEUTRAL),
      AXIS, List.of(AXIS, NEUTRAL),
      NEUTRAL, List.of(NEUTRAL));

  public Side oppositeSide() {
    return OPPOSITE_MAP.get(this);
  }

  public List<Side> getFriendlySides() {
    return FRIENDLY_MAP.get(this);
  }

  public String toLower() {
    return value.toLowerCase(Locale.ROOT);
  }

  @Override
  public String toString() {
    return value;
  }

  public static Stream<Side> stream() {
    return Stream.of(Side.values());
  }

  public static Stream<Side> combatants() {
    return Stream.of(ALLIES, AXIS);
  }
}
