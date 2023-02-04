package com.enigma.watatsea.model;

import com.enigma.waratsea.model.map.GameMap;
import com.enigma.waratsea.model.map.Grid;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.map.GridType.LAND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {
  private final GameMap gameMap = new GameMap();

  @Test
  void testAddGrid() {
    var row = 0;
    var column = 0;
    var reference = "A1";
    var name = "name";

    var grid1 = Grid.builder()
        .row(row)
        .column(column)
        .reference(reference)
        .name(name)
        .type(LAND)
        .build();

    gameMap.addGrid(grid1);

    var resultByRowColumn = gameMap.getGridByRowColumn(row, column);
    var resultByReference = gameMap.getGridFromReference(reference);
    var resultByName = gameMap.getGridFromName(name);

    assertEquals(grid1, resultByRowColumn);
    assertEquals(grid1, resultByReference);
    assertEquals(grid1, resultByName);
  }
}
