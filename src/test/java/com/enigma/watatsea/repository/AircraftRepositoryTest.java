package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.aircraft.AttackEntity;
import com.enigma.waratsea.entity.aircraft.PerformanceEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Frame;
import com.enigma.waratsea.model.squadron.SquadronConfiguration;
import com.enigma.waratsea.repository.impl.AircraftRepositoryImpl;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Nation.BRITISH;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.aircraft.AircraftType.TORPEDO_BOMBER;
import static com.enigma.waratsea.model.aircraft.AltitudeType.MEDIUM;
import static com.enigma.waratsea.model.aircraft.LandingType.LAND;
import static com.enigma.waratsea.model.aircraft.ServiceType.AIR_FORCE;
import static com.enigma.waratsea.model.squadron.SquadronStrength.FULL;
import static com.enigma.waratsea.model.squadron.SquadronStrength.HALF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AircraftRepositoryTest {
  @InjectMocks
  private AircraftRepositoryImpl aircraftRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String AIRCRAFT_DIRECTORY = Paths.get("aircraft", "data").toString();
  private static final String AIRCRAFT_NAME = "Beaufort";

  @Test
  void shouldGetAircraftEntity() {
    var aircraftId = new Id(ALLIES, AIRCRAFT_NAME);

    var inputStream = getInputStream();

    given(resourceProvider.getResourceInputStream(aircraftId, AIRCRAFT_DIRECTORY)).willReturn(inputStream);

    var result = aircraftRepository.get(aircraftId);

    assertNotNull(result);
    assertEquals(aircraftId, result.getId());
    assertEquals(TORPEDO_BOMBER, result.getType());
    assertEquals(BRITISH, result.getNation());
    assertEquals(AIR_FORCE, result.getService());
    assertEquals("RAF-", result.getDesignation());
    assertEquals(MEDIUM, result.getAltitude());
    assertEquals(LAND, result.getTakeOff());
    assertEquals(LAND, result.getLanding());
    assertEquals(buildNavalAttack(), result.getNavalWarship());
    assertEquals(buildNavalAttack(), result.getNavalTransport());
    assertEquals(buildLandAttack(), result.getLand());
    assertEquals(buildAirAttack(), result.getAir());
    assertEquals(buildPerformance(), result.getPerformance());
    assertEquals(buildFrame(), result.getFrame());
    assertEquals(buildConfiguration(), result.getConfiguration());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get(  "/", AIRCRAFT_DIRECTORY, AIRCRAFT_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }

  private AttackEntity buildNavalAttack() {
    return AttackEntity.builder()
        .modifier(1)
        .factor(Map.of(FULL, 4, HALF, 2))
        .build();
  }

  private AttackEntity buildLandAttack() {
    return AttackEntity.builder()
        .modifier(0)
        .factor(Map.of(FULL, 1, HALF, 0))
        .build();
  }

  private AttackEntity buildAirAttack() {
    return AttackEntity.builder()
        .modifier(0)
        .factor(Map.of(FULL, 2, HALF, 1))
        .defensive(true)
        .build();
  }

  private PerformanceEntity buildPerformance() {
    return PerformanceEntity.builder()
        .range(16)
        .endurance(1)
        .build();
  }

  private Frame buildFrame() {
    return new Frame(2, false);
  }

  private Set<SquadronConfiguration> buildConfiguration() {
    return Set.of(SquadronConfiguration.NONE, SquadronConfiguration.SEARCH);
  }
}
