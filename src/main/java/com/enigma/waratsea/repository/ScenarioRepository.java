package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.Scenario;
import com.enigma.waratsea.exceptions.ScenarioException;
import com.enigma.waratsea.resource.ResourceNames;
import com.enigma.waratsea.resource.ResourceProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class ScenarioRepository {
    private static final String SUMMARY_FILE_NAME = "summary.json";

    private final ResourceNames resourceNames;
    private final ResourceProvider resourceProvider;


    @Inject
    public ScenarioRepository(final ResourceNames resourceNames,
                              final ResourceProvider resourceProvider) {
        this.resourceNames = resourceNames;
        this.resourceProvider = resourceProvider;
    }

    /**
     * Get all the defined scenarios for the current game.
     *
     * We must dynamically get the scenario names since these are not known ahead of time.
     * The directories under the scenario directory equate to the scenario names for the current game.
     *
     * @return The scenario names for the current game.
     */
    public List<Scenario> get() {
        return getScenarioNames()
                .stream()
                .map(this::createScenario)
                .collect(Collectors.toList());
    }


    private List<String> getScenarioNames()  {
        return resourceProvider.getSubDirectoryPaths(resourceNames.getScenarioDirectory())
                .stream()
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
    }

    private Scenario createScenario(final String scenarioName) {
        try (InputStream in = getScenarioInputStream(scenarioName);
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {
            return readScenario(br);
        } catch (IOException e) {
            throw new ScenarioException("Unable to create scenario:" + scenarioName);
        }
    }

    private InputStream getScenarioInputStream(final String scenarioName) {
        var resourceName = Paths.get(resourceNames.getScenarioDirectory(), scenarioName, SUMMARY_FILE_NAME).toString();
        return resourceProvider.getResourceInputStream(resourceName);
    }

    private Scenario readScenario(final BufferedReader bufferedReader)  {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson gson = gsonBuilder.create();

        Scenario scenario = gson.fromJson(bufferedReader, Scenario.class);

        log.debug("load scenario: {}", scenario.getTitle());

        return scenario;
    }


}
