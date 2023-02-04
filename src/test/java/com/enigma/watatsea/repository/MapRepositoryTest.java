package com.enigma.watatsea.repository;

import com.enigma.waratsea.repository.impl.MapRepositoryImpl;
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

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.map.GridType.LAND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MapRepositoryTest {
  @InjectMocks
  private MapRepositoryImpl mapRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String MAP_DIRECTORY = "maps";
  private static final String MAP = "map";
  private static final String MAP_WITHOUT_GRIDS = "mapWithoutGrids";
  private static final int ROWS = 34;
  private static final int COLUMNS = 77;
  private static final String DEFAULT_GRID_NAME = "--";

  @Test
  void shouldGetGameMapEntity() {
    var inputStream = getInputStream(MAP);

    given(resourceProvider.getDefaultResourceInputStream(anyString())).willReturn(inputStream);

    var result = mapRepository.get();

    assertNotNull(result);
    assertEquals(ROWS, result.getRows());
    assertEquals(COLUMNS, result.getColumns());
    assertEquals(COLUMNS, result.getColumns());
    assertEquals(DEFAULT_GRID_NAME, result.getDefaultGridName());
    assertEquals(LAND, result.getDefaultGridType());
    assertNotNull(result.getLocations());
    assertFalse(result.getLocations().isEmpty());
    assertNotNull(result.getGrids());
    assertFalse(result.getGrids().isEmpty());
  }

  @Test
  void shouldGetGameMapEntityWithoutGrids() {
    var inputStream = getInputStream(MAP_WITHOUT_GRIDS);

    given(resourceProvider.getDefaultResourceInputStream(anyString())).willReturn(inputStream);

    var result = mapRepository.get();

    assertNotNull(result);
    assertEquals(ROWS, result.getRows());
    assertEquals(COLUMNS, result.getColumns());
    assertEquals(COLUMNS, result.getColumns());
    assertEquals(DEFAULT_GRID_NAME, result.getDefaultGridName());
    assertEquals(LAND, result.getDefaultGridType());
    assertNotNull(result.getLocations());
    assertFalse(result.getLocations().isEmpty());
    assertNotNull(result.getGrids());
    assertTrue(result.getGrids().isEmpty());
  }

  private InputStream getInputStream(String mapName) {
    var fullPath = Paths.get("/", MAP_DIRECTORY, mapName + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
