package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Game {
  private final GameName gameName;

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

  public void setId(final String suffix) {
    id = String.join("-", scenario.getName(), suffix);
  }

  public void nextTurn() {
    turn = turn.next();
  }

  public Weather setStartingWeather() {
    return scenario.getWeather();
  }

  private Turn setStartingTurn() {
    return Turn.builder()
        .timeRange(scenario.getTimeRange())
        .date(scenario.getDate())
        .build();
  }
}
