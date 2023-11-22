package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.ScenarioEntity;
import com.enigma.waratsea.exception.ScenarioException;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.enigma.waratsea.repository.serializer.LocalDateDeserializer;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
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

@Slf4j
@Singleton
public class ScenarioRepositoryImpl implements ScenarioRepository {
  private final Props props;
  private final GamePaths gamePaths;
  private final ResourceProvider resourceProvider;

  @Inject
  public ScenarioRepositoryImpl(final @Named("App") Props props,
                                final GamePaths gamePaths,
                                final ResourceProvider resourceProvider) {
    this.props = props;
    this.gamePaths = gamePaths;
    this.resourceProvider = resourceProvider;
  }

  @Override
  public List<ScenarioEntity> get() {
    return getScenarioNames()
        .stream()
        .map(this::createScenario)
        .collect(Collectors.toList());
  }

  private List<String> getScenarioNames() {
    return resourceProvider.getSubDirectoryPaths(gamePaths.getScenarioDirectory())
        .stream()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
  }

  private ScenarioEntity createScenario(final String scenarioName) {
    try (var in = getInputStream(scenarioName);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return readScenario(br);
    } catch (IOException e) {
      throw new ScenarioException("Unable to create scenario: " + scenarioName);
    }
  }

  private InputStream getInputStream(final String scenarioName) {
    var scenarioDirectoryName = gamePaths.getScenarioDirectory();
    var scenarioSummaryFileName = gamePaths.getSummaryFileName();
    var resourceName = Paths.get(scenarioDirectoryName, scenarioName, scenarioSummaryFileName).toString();
    return resourceProvider.getDefaultInputStream(resourceName);
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
