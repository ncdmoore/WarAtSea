package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.GameMapEntity;
import com.enigma.waratsea.repository.MapRepository;
import com.enigma.waratsea.service.impl.MapServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.enigma.waratsea.model.GridType.LAND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MapServiceTest {
  @InjectMocks
  private MapServiceImpl mapService;

  @Mock
  private MapRepository mapRepository;

  private static final int ROWS = 20;
  private static final int COLUMNS = 30;
  private static final int NUMBER_OF_GRIDS = (ROWS * COLUMNS) - (COLUMNS / 2);
  private static final String DEFAULT_GRID_NAME = "default";
  private static final Map<String, String> LOCATIONS = Map.of(
    "A1", "named-a1",
    "B15", "named-b15"
  );

  @Test
  void testGetMap() {
    var mapEntity = new GameMapEntity(ROWS, COLUMNS, DEFAULT_GRID_NAME, LAND, LOCATIONS);

    given(mapRepository.get()).willReturn(mapEntity);

    var gameMap = mapService.get();

    assertNotNull(gameMap);
    assertEquals(NUMBER_OF_GRIDS, gameMap.getGrids().size());
    assertEquals("A1", gameMap.getGridByRowColumn(0,0).getReference());
    assertEquals("named-a1", gameMap.getGridFromName("named-a1").getName());
    assertEquals(LAND, gameMap.getGridByRowColumn(0,0).getType());

    assertEquals(1, gameMap.getGridFromReference("B15").getColumn());
    assertEquals(14, gameMap.getGridFromName("named-b15").getRow());
    assertEquals("named-b15", gameMap.getGridFromReference("B15").getName());

    assertEquals(DEFAULT_GRID_NAME, gameMap.getGridFromReference("A2").getName());
    assertEquals(LAND, gameMap.getGridByRowColumn(1,0).getType());
    assertNull(gameMap.getGridFromName("named-a2"));
  }
}
