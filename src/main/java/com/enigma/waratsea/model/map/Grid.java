package com.enigma.waratsea.model.map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Grid {
  private final int row;
  private final int column;
  private final String reference;
  private final String name;
  private final GridType type;
}
