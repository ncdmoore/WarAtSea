package com.enigma.waratsea.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Game turn.
 */
@Data
@Builder
public class Turn {
  private TurnType turnType;
  private VisibilityType visibilityType;
  private int number;
  private LocalDate date;
}
