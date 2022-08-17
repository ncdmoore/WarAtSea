package com.enigma.waratsea.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WeatherType {
    CLEAR("Clear"),
    CLOUDY("Cloudy"),
    RAIN("Rain"),
    SQUALL("Squall"),
    STORM("Storm"),
    GALE("Gale");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
