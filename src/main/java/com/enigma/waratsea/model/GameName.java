package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum GameName {
  ARCTIC_CONVOY("arcticConvoy"),
  BOMB_ALLEY("bombAlley"),
  CORAL_SEA("coralSea"),
  EASTERN_FLEET("easternFleet");

  private final String value;

  private static final Map<String, GameName> CONVERSION_MAP = Map.of(
      "arcticConvoy", GameName.ARCTIC_CONVOY,
      "bombAlley", GameName.BOMB_ALLEY,
      "coralSea", GameName.CORAL_SEA,
      "easternFleet", GameName.EASTERN_FLEET
  );

  public static boolean isValid(final String value) {
    return CONVERSION_MAP.containsKey(value);
  }

  public static GameName convert(final String value) {
    return CONVERSION_MAP.get(value);
  }
}
