package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.*;
import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.repository.GameRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.service.WeatherService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class GameServiceImpl implements GameService {
  private final WeatherService weatherService;
  private final GameRepository gameRepository;
  private final GameMapper gameMapper;
  private GameName gameName;

  @Getter
  private Game game;

  @Inject
  public GameServiceImpl(final Events events,
                         final WeatherService weatherService,
                         final GameRepository gameRepository,
                         final GameMapper gameMapper) {
    this.weatherService = weatherService;
    this.gameRepository = gameRepository;
    this.gameMapper = gameMapper;

    registerEvents(events);
  }

  @Override
  public List<Game> get() {
    return gameRepository
        .get()
        .stream()
        .map(gameMapper::toModel)
        .sorted()
        .collect(Collectors.toList());
  }

  private void registerEvents(final Events events) {
    events.getGameNameEvents().register(this::setGameName);
    events.getNewGameEvents().register(this::create);
    events.getSaveGameEvents().register(this::save);
    events.getScenarioEvents().register(this::setScenario);
    events.getSideEvents().register(this::setHumanSide);
  }

  private void nextTurn() {
    game.nextTurn();
  }

  private void determineWeather() {
    var weatherInput = WeatherInput
        .builder()
        .weather(game.getWeather())
        .turn(game.getTurn())
        .build();

    var newWeather = weatherService.determine(weatherInput);
    game.setWeather(newWeather);
  }

  private void setGameName(final GameNameEvent gameEvent) {
    gameName = gameEvent.gameName();
    log.debug("Game Service received gameNameEvent, game set to: '{}'", gameEvent.gameName());
  }

  private void create(final NewGameEvent newGameEvent) {
    game = new Game(gameName);
    log.debug("Game Service received newGameEvent");
  }

  private void save(final SaveGameEvent gameSaveEvent) {
    game.createId(gameSaveEvent.getName());
    var gameMapper = GameMapper.INSTANCE;
    var gameEntity = gameMapper.toEntity(game);

    gameRepository.save(gameEntity);
    log.debug("Game Service received gameSaveEvent, save: '{}'", game.getId());
  }

  private void setScenario(final ScenarioEvent scenarioEvent) {
    game.setScenario(scenarioEvent.getScenario());
    log.debug("Game Service received scenarioEvent, scenario set to: '{}'", scenarioEvent.getScenario().getTitle());
  }

  private void setHumanSide(final SideEvent sideEvent) {
    game.setHumanSide(sideEvent.getSide());
    log.debug("Game Service received sideEvent, side set to: '{}'", sideEvent.getSide());
  }
}
