package com.enigma.waratsea.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Game implements Comparable<Game> {
  private final GameName gameName;

  @EqualsAndHashCode.Include
  @Setter
  private String id;

  private Scenario scenario;

  @Setter
  private Side humanSide;

  @Setter
  private Turn turn;

  @Setter
  private Weather weather;

  public Game(final GameName gameName) {
    this.gameName = gameName;
  }

  public void setScenario(final Scenario scenario) {
    this.scenario = scenario;
    weather = setStartingWeather();
    turn = setStartingTurn();
  }

  public void createId(final String suffix) {
    id = String.join("-", scenario.getName(), suffix);
  }

  public void nextTurn() {
    turn = turn.next();
  }

  @Override
  public String toString() {
    return id;
  }

  @Override
  public int compareTo(@NotNull Game o) {
    return id.compareTo(o.id);
  }

  private Weather setStartingWeather() {
    return scenario.getWeather();
  }

  private Turn setStartingTurn() {
    return Turn.builder()
        .timeRange(scenario.getTimeRange())
        .date(scenario.getDate())
        .build();
  }


}
