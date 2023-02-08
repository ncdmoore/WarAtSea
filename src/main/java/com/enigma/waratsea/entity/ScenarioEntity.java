package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.model.squadron.SquadronDeploymentType;
import com.enigma.waratsea.model.turn.TimeRange;
import com.enigma.waratsea.model.weather.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static com.enigma.waratsea.model.squadron.SquadronDeploymentType.VARIABLE;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @Builder.Default
  private Set<NationId> nationsWithAllotmentOptions = Collections.emptySet();
}
