package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TurnType {
    DAY("Day"),
    TWILIGHT("Twilight"),
    NIGHT("Night") ;

    @Getter
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
