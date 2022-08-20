package com.enigma.waratsea.resource;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Singleton
@Getter
public class ResourceNames {
    @Setter
    private String gameName;

    private final String gameDirectory = "game";
    private final String cssDirectory = "css";
    private final String imageDirectory = "images";
    private final String scenarioDirectory = "scenarios";
    private final String summaryFileName = "summary.json";

}
