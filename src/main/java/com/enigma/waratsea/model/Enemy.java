package com.enigma.waratsea.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Enemy {
  private Id id;
  private String type;
}
