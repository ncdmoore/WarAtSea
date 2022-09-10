package com.enigma.watatsea.model;

import com.enigma.waratsea.model.GameMap;
import com.enigma.waratsea.model.Grid;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.GridType.LAND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {
  private final GameMap gameMap = new GameMap();

  @Test
  void testAddGrid() {
    var row = 0;
    var column = 0;
    var reference = "A1";

    var grid1 = Grid.builder()
        .row(row)
        .column(column)
        .reference(reference)
        .type(LAND)
        .build();

    gameMap.addGrid(grid1);

    var resultByRowColumn = gameMap.getGrid(row, column);
    var resultByReference = gameMap.getGrid(reference);

    assertEquals(grid1, resultByRowColumn);
    assertEquals(grid1, resultByReference);
  }
}
