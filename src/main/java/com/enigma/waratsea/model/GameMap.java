package com.enigma.waratsea.model;

import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.HashMap;
import java.util.Map;

public class GameMap {
  private final MultiKeyMap<Integer, Grid> gridMap = new MultiKeyMap<>();
  private final Map<String, Grid> referenceMap = new HashMap<>();

  public void addGrid(final Grid grid) {
    var row = grid.getRow();
    var column = grid.getColumn();
    var reference = grid.getReference();

    gridMap.put(row, column, grid);
    referenceMap.put(reference, grid);
  }

  public Grid getGrid(final int row, final int column) {
    return gridMap.get(row, column);
  }

  public Grid getGrid(final String reference) {
    return referenceMap.get(reference);
  }
}
