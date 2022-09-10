package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GridType;
import lombok.Data;

import java.util.Map;

@Data
public class GameMapEntity {
  private int rows;
  private int columns;
  private Map<String, GridType> grids;
}
