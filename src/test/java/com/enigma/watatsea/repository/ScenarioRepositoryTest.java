package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.property.AppProps;
import com.enigma.waratsea.repository.impl.ResourceNames;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import com.enigma.waratsea.repository.impl.ScenarioRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Visibility.GOOD;
import static com.enigma.waratsea.model.WeatherType.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ScenarioRepositoryTest {
  @InjectMocks
  private ScenarioRepositoryImpl scenarioRepository;

  @Mock
  private AppProps props;

  @Spy
  private ResourceNames resourceNames;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String DATE_FORMAT = "MM/dd/yyyy";
  private static final String SCENARIO_DIRECTORY = "scenarios";
  private static final String SUMMARY = "summary";

  @Test
  void shouldGetScenarioEntities() {
    var paths = List.of(Path.of("scenario"));

    var scenarioDirectory = resourceNames.getScenarioDirectory();

    var inputStream = getInputStream();

    given(props.getString(anyString())).willReturn(DATE_FORMAT);
    given(resourceProvider.getSubDirectoryPaths(scenarioDirectory)).willReturn(paths);
    given(resourceProvider.getDefaultResourceInputStream(anyString())).willReturn(inputStream);

    var results = scenarioRepository.get();

    assertNotNull(results);
    assertEquals(1, results.size());

    var scenario = results.get(0);

    assertEquals(1, scenario.getId());
    assertEquals("1-FirstSortie", scenario.getName());
    assertEquals("The First Sortie", scenario.getTitle());
    assertEquals("firstSortie.png", scenario.getImage());
    assertEquals(LocalDate.of(1940, 6, 11), scenario.getDate());
    assertEquals(Weather.builder().weatherType(CLEAR).visibility(GOOD).build(), scenario.getWeather());
    assertEquals(48, scenario.getMaxTurns());
    assertNotNull(scenario.getDescription());
    assertEquals("june1940", scenario.getMap());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", SCENARIO_DIRECTORY, SUMMARY + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
