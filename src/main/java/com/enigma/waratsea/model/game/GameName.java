package com.enigma.waratsea.model.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum GameName {
    ARCTIC_CONVOY("arcticConvoy"),
    BOMB_ALLEY("bombAlley"),
    CORAL_SEA("coralSea");

    private final String value;

    private static final Map<String, GameName> CONVERSION_MAP = Map.of(
            "arcticConvoy", GameName.ARCTIC_CONVOY,
            "bombAlley", GameName.BOMB_ALLEY,
            "coralSea", GameName.CORAL_SEA
    );

    public static GameName convert(final String value) {
        return CONVERSION_MAP.get(value);
    }
}
