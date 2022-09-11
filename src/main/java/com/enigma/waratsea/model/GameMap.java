package com.enigma.waratsea.model;

import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {
  private final MultiKeyMap<Integer, Grid> gridMap = new MultiKeyMap<>();
  private final Map<String, Grid> referenceMap = new HashMap<>();
  private final Map<String, Grid> namedMap = new HashMap<>();

  public void addGrid(final Grid grid) {
    var row = grid.getRow();
    var column = grid.getColumn();
    var reference = grid.getReference();
    var name = grid.getName();

    gridMap.put(row, column, grid);
    referenceMap.put(reference, grid);
    namedMap.put(name, grid);
  }

  public List<Grid> getGrids() {
    return referenceMap.values().stream().toList();
  }

  public Grid getGridByRowColumn(final int row, final int column) {
    return gridMap.get(row, column);
  }

  public Grid getGridFromReference(final String reference) {
    return referenceMap.get(reference);
  }

  public Grid getGridFromName(final String name) {
    return namedMap.get(name);
  }
}
