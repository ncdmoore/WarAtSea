package com.enigma.watatsea.model;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.player.HumanPlayer;
import com.enigma.waratsea.model.weather.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;
import static com.enigma.waratsea.model.turn.TimeRange.DAY_2;
import static com.enigma.waratsea.model.weather.Visibility.GOOD;
import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
  private Game game;
  private Scenario scenario;

  @BeforeEach
  void setUp() {
    game = new Game(BOMB_ALLEY);
    scenario = buildScenario();
  }

  @Test
  void testSetScenario() {
    game.setScenario(scenario);

    assertEquals(scenario.getWeather(), game.getWeather());
    assertEquals(1, game.getTurn().getNumber());
    assertEquals(scenario.getDate(), game.getTurn().getDate());
    assertEquals(scenario.getTimeRange(), game.getTurn().getTimeRange());
  }

  @Test
  void testAddPlayers() {
    var player = new HumanPlayer(ALLIES);

    game.addPlayer(player);

    assertEquals(player, game.getPlayers().get(ALLIES));
  }

  @Test
  void testGetHumanPlayer() {
    game.setHumanSide(AXIS);
    var player = new HumanPlayer(AXIS);
    game.addPlayer(player);

    assertEquals(player, game.getHuman());
  }

  @Test
  void testCreateId() {
    var suffix = "suffix";
    game.setScenario(scenario);
    game.createId(suffix);

    assertEquals(scenario.getName() + "-" + suffix, game.getId());
  }

  @Test
  void testNextTurn() {
    var events = new Events();

    game.setScenario(scenario);
    game.processTurn(events);
    var turn = game.getTurn();

    assertEquals(2, turn.getNumber());
    assertEquals(LocalDate.now(), turn.getDate());
    assertEquals(DAY_2, turn.getTimeRange());
  }

  private Scenario buildScenario() {
    var scenario = new Scenario();
    scenario.setName("test-scenario");
    scenario.setWeather(buildWeather());
    scenario.setDate(LocalDate.now());
    scenario.setTimeRange(DAY_1);
    return scenario;
  }

  private Weather buildWeather() {
    return Weather.builder()
        .weatherType(CLEAR)
        .visibility(GOOD)
        .build();
  }
}
