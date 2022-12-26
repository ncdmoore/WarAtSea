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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
  private static final String SQUADRON_NAME = "Albacore-M1";
  private static final String AIRCRAFT_NAME = "Albacore";

  @Test
  void shouldGetSquadronEntity() {
    var squadronId = new Id(ALLIES, SQUADRON_NAME);

    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(squadronId, SQUADRON_DIRECTORY)).willReturn(inputStream);

    var result = squadronRepository.get(squadronId);

    assertNotNull(result);
    assertEquals(squadronId, result.getId());
    assertEquals(ALLIES, result.getAircraft().getSide());
    assertEquals(AIRCRAFT_NAME, result.getAircraft().getName());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", SQUADRON_DIRECTORY, SQUADRON_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
