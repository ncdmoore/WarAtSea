package com.enigma.waratsea.model;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.phase.WeatherEvent;
import com.enigma.waratsea.model.player.Player;
import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

  private final Map<Side, Player> players = new HashMap<>();

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

  public void addPlayer(final Player player) {
    players.put(player.getSide(), player);
  }

  public Player getHuman() {
    return players.get(humanSide);
  }

  public void processTurn(final Events events) {
    updateTurn();
    determineWeather(events);
  }

  @Override
  public String toString() {
    return id;
  }

  @Override
  public int compareTo(@NotNull final Game o) {
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

  private void updateTurn() {
    turn = turn.next();
  }

  private void determineWeather(final Events events) {
    events.getWeatherEvent().fire(new WeatherEvent());
  }
}
