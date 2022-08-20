package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The player sides of the game.
 */
@RequiredArgsConstructor
@Getter
public enum Side {
    ALLIES("Allies"),
    AXIS("Axis"),
    NEUTRAL("Neutral");

    private final String value;

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

    @Override
    public String toString() {
        return value;
    }

    public static Stream<Side> stream() {
        return Stream.of(Side.values());
    }
}
