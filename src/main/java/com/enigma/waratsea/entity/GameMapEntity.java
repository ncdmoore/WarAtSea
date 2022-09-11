package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GridType;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
public class GameMapEntity {
  private final int rows;
  private final int columns;
  private final String defaultGridName;
  private final GridType defaultGridType;
  private final Map<String, String> locations;
  private Map<String, GridType> grids = Collections.emptyMap();
}
