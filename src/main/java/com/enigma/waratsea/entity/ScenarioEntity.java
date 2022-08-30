package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.SquadronDeploymentType;
import com.enigma.waratsea.model.TimeRange;
import com.enigma.waratsea.model.Weather;
import lombok.Data;

import java.time.LocalDate;

import static com.enigma.waratsea.model.SquadronDeploymentType.HUMAN;
import static com.enigma.waratsea.model.TimeRange.DAY_1;

@Data
public class ScenarioEntity {
  private Integer id;
  private String name;
  private String title;
  private String image;
  private String description;
  private LocalDate date;
  private Weather weather;
  private int maxTurns;
  private TimeRange timeRange = DAY_1;
  private String map;
  private String objectives;
  private SquadronDeploymentType squadron = HUMAN;
  private boolean minefieldForHumanSide;
  private boolean flotillasForHumanSide;
}
