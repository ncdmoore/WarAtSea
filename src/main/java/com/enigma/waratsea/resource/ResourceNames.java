package com.enigma.waratsea.resource;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Singleton
@Getter
public class ResourceNames {
    @Setter
    private String gameName;
    private final String scenarioDirectory = "scenarios";
    private final String summaryFileName = "summary.json";

}
