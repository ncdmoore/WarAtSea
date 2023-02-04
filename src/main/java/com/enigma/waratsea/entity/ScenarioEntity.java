package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.squadron.SquadronDeploymentType;
import com.enigma.waratsea.model.turn.TimeRange;
import com.enigma.waratsea.model.weather.Weather;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import static com.enigma.waratsea.model.squadron.SquadronDeploymentType.VARIABLE;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;

@Data
@Builder
public class ScenarioEntity {
  private Integer id;
  private String name;
  private String title;
  private String image;
  private String description;
  private LocalDate date;
  private Weather weather;
  private int maxTurns;

  @Builder.Default
  private TimeRange timeRange = DAY_1;

  private String map;
  private String timeFrame;

  @Builder.Default
  private SquadronDeploymentType squadron = VARIABLE;
  private boolean minefieldForHumanSide;
  private boolean flotillasForHumanSide;
}
