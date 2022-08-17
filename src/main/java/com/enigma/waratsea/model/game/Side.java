package com.enigma.waratsea.model.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Side {
    ALLIES("Allies"),
    AXIS("Axis"),
    NEUTRAL("Neutral");

    private final String value;

    public Side opposite() {
        switch (this) {
            case ALLIES:
                return AXIS;
            case AXIS:
                return ALLIES;
            default:
                return NEUTRAL;
        }
    }

    public List<Side> getFriendly() {
        switch (this) {
            case ALLIES:
                return List.of(ALLIES, NEUTRAL);
            case AXIS:
                return List.of(AXIS, NEUTRAL);
            default:
                return List.of(NEUTRAL);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public static Stream<Side> stream() {
        return Stream.of(Side.values());
    }
}
