package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.RegionRepositoryImpl;
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
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Nation.ITALIAN;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegionRepositoryTest {
  @InjectMocks
  private RegionRepositoryImpl regionRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String REGION_DIRECTORY = "regions";
  private static final String MAP_NAME = "april1941";

  private static final String REGION_NAME = "East";
  private static final String GRID_REFERENCE = "BD25";
  private static final List<String> airfields = List.of("Rhodes", "Scarpanto");
  private static final List<String> ports = List.of("Rhodes");

  @Test
  void shouldGetRegionEntities() {
    var mapId = new Id(AXIS, MAP_NAME);

    var inputStream = getInputStream();
    var regionBaseDirectory = gamePaths.getRegionPath();

    given(resourceProvider.getResourceInputStream(mapId, regionBaseDirectory)).willReturn(inputStream);

    var result = regionRepository.get(mapId);

    assertNotNull(result);
    assertEquals(9, result.size());

    var italianEastRegion = result
        .stream()
        .filter(region -> region.getName().equals(REGION_NAME) && region.getNation() == ITALIAN)
        .findFirst();

    assertTrue(italianEastRegion.isPresent());
    assertEquals(REGION_NAME, italianEastRegion.get().getName());
    assertEquals(ITALIAN, italianEastRegion.get().getNation());
    assertEquals("4", italianEastRegion.get().getMin());
    assertEquals("0", italianEastRegion.get().getMax());
    assertEquals(GRID_REFERENCE, italianEastRegion.get().getGridReference());
    assertEquals(2, italianEastRegion.get().getAirfields().size());

    var airfieldNames = italianEastRegion.get().getAirfields().stream().map(Id::getName).toList();

    airfields.forEach(airfield -> assertTrue(airfieldNames.contains(airfield)));

    assertEquals(1, italianEastRegion.get().getPorts().size());

    var portNames = italianEastRegion.get().getPorts().stream().map(Id::getName).toList();

    ports.forEach(port -> assertTrue(portNames.contains(port)));
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", REGION_DIRECTORY, MAP_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
