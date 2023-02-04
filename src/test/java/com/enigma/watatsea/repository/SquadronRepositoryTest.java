package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.SquadronRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.squadron.SquadronConfiguration.NONE;
import static com.enigma.waratsea.model.squadron.SquadronConfiguration.SEARCH;
import static com.enigma.waratsea.model.squadron.SquadronState.*;
import static com.enigma.waratsea.model.squadron.SquadronStrength.FULL;
import static com.enigma.waratsea.model.squadron.SquadronStrength.HALF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SquadronRepositoryTest {
  @InjectMocks
  private SquadronRepositoryImpl squadronRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String SQUADRON_DIRECTORY = Paths.get("squadrons", "data").toString();
  private static final String SQUADRON_NAME = "squadron";
  private static final String SQUADRON_WITH_DEFAULTS_NAME = "squadronWithDefaults";
  private static final String AIRCRAFT_NAME = "Albacore";

  @Test
  void shouldGetSquadronEntity() {
    var squadronId = new Id(ALLIES, SQUADRON_NAME);

    var inputStream = getInputStream(SQUADRON_NAME);

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = squadronRepository.get(squadronId);

    assertNotNull(result);
    assertEquals(squadronId, result.getId());
    assertEquals(ALLIES, result.getAircraft().getSide());
    assertEquals(AIRCRAFT_NAME, result.getAircraft().getName());
    assertEquals(HALF, result.getStrength());
    assertEquals(ON_PATROL, result.getState());
    assertEquals(SEARCH, result.getConfiguration());
  }

  @Test
  void shouldGetSquadronWithDefaultsEntity() {
    var squadronId = new Id(ALLIES, SQUADRON_WITH_DEFAULTS_NAME);

    var inputStream = getInputStream(SQUADRON_WITH_DEFAULTS_NAME);

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = squadronRepository.get(squadronId);

    assertNotNull(result);
    assertEquals(squadronId, result.getId());
    assertEquals(ALLIES, result.getAircraft().getSide());
    assertEquals(AIRCRAFT_NAME, result.getAircraft().getName());
    assertEquals(FULL, result.getStrength());
    assertEquals(CREATED, result.getState());
    assertEquals(NONE, result.getConfiguration());
  }

  private InputStream getInputStream(final String squadronName) {
    var fullPath = Paths.get("/", SQUADRON_DIRECTORY, squadronName + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
