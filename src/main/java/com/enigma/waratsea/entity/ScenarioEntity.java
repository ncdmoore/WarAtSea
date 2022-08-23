package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.SquadronDeploymentType;
import com.enigma.waratsea.model.TurnType;
import com.enigma.waratsea.model.WeatherType;
import lombok.Data;

import java.time.LocalDate;

/**
 * This represents a Scenario's persisted data.
 */
@Data
public class ScenarioEntity {
  private Integer id;
  private String name;
  private String title;
  private String image;
  private String description;
  private LocalDate date;
  private WeatherType weather;
  private int maxTurns;
  private TurnType turnType;
  private String map;
  private String objectives;
  private SquadronDeploymentType squadron;
  private boolean minefieldForHumanSide;
  private boolean flotillasForHumanSide;
}
