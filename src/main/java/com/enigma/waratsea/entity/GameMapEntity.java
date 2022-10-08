package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GridType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameMapEntity {
  private int rows;
  private int columns;
  private String defaultGridName;
  private GridType defaultGridType;
  private Map<String, String> locations;
  private Map<String, GridType> grids = Collections.emptyMap();
}
