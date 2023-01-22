package com.enigma.waratsea.model.mission;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class Bombardment implements Mission {
  private String id;
  private int priority;
  private String description;
  private Set<String> taskForces;
}
