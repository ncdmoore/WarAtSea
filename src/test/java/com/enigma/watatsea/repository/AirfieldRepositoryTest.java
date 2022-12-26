package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.AirfieldRepositoryImpl;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.aircraft.LandingType.SEAPLANE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AirfieldRepositoryTest {
  @InjectMocks
  private AirfieldRepositoryImpl airfieldRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths dataGamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String AIRFIELD_DIRECTORY = "airfields";
  private static final String AIRFIELD_NAME = "Altafjord";
  private static final String GRID_REFERENCE = "AQ26";

  @Test
  void shouldGetAirfieldEntity() {
    var airfieldId = new Id(AXIS, AIRFIELD_NAME);

    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(airfieldId, AIRFIELD_DIRECTORY)).willReturn(inputStream);

    var result = airfieldRepository.get(airfieldId);

    assertNotNull(result);
    assertEquals(airfieldId, result.getId());
    assertEquals(AIRFIELD_NAME, result.getTitle());
    assertEquals(List.of(SEAPLANE), result.getLandingTypes());
    assertEquals(10, result.getMaxCapacity());
    assertEquals(10, result.getCapacity());
    assertEquals(10, result.getMaxAntiAir());
    assertEquals(10, result.getAntiAir());
    assertEquals(GRID_REFERENCE, result.getGridReference());
    assertNotNull(result.getSquadrons());
    assertTrue(result.getSquadrons().isEmpty());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get(  "/", AIRFIELD_DIRECTORY, AIRFIELD_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
