package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.ScenarioEntity;
import com.enigma.waratsea.exceptions.ScenarioException;
import com.enigma.waratsea.property.AppProps;
import com.enigma.waratsea.resource.ResourceNames;
import com.enigma.waratsea.resource.ResourceProvider;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class ScenarioRepository {
  private final AppProps props;
  private final ResourceNames resourceNames;
  private final ResourceProvider resourceProvider;

  @Inject
  ScenarioRepository(final AppProps props,
                     final ResourceNames resourceNames,
                     final ResourceProvider resourceProvider) {
    this.props = props;
    this.resourceNames = resourceNames;
    this.resourceProvider = resourceProvider;
  }

  public List<ScenarioEntity> get() {
    return getScenarioNames()
        .stream()
        .map(this::createScenario)
        .collect(Collectors.toList());
  }

  private List<String> getScenarioNames() {
    return resourceProvider.getSubDirectoryPaths(resourceNames.getScenarioDirectory())
        .stream()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
  }

  private ScenarioEntity createScenario(final String scenarioName) {
    try (var in = getScenarioInputStream(scenarioName);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return readScenario(br);
    } catch (IOException e) {
      throw new ScenarioException("Unable to create scenario:" + scenarioName);
    }
  }

  private InputStream getScenarioInputStream(final String scenarioName) {
    var scenarioDirectoryName = resourceNames.getScenarioDirectory();
    var scenarioSummaryFileName = resourceNames.getSummaryFileName();
    var resourceName = Paths.get(scenarioDirectoryName, scenarioName, scenarioSummaryFileName).toString();
    return resourceProvider.getResourceInputStream(resourceName);
  }

  private ScenarioEntity readScenario(final BufferedReader bufferedReader) {
    var dateFormat = props.getString("scenario.date.format");
    var gsonBuilder = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer(dateFormat));
    var gson = gsonBuilder.create();

    var scenario = gson.fromJson(bufferedReader, ScenarioEntity.class);

    log.debug("load scenario: {}", scenario.getTitle());

    return scenario;
  }
}
