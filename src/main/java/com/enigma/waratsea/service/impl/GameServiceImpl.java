package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.GameNameEvent;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.event.user.SelectSavedGameEvent;
import com.enigma.waratsea.event.user.SelectScenarioEvent;
import com.enigma.waratsea.event.user.SelectSideEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.repository.GameRepository;
import com.enigma.waratsea.service.GameService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class GameServiceImpl implements GameService {
  private final GameRepository gameRepository;
  private final GameMapper gameMapper;
  private GameName gameName;

  @Getter
  private Game game;

  @Inject
  public GameServiceImpl(final Events events,
                         final GameRepository gameRepository,
                         final GameMapper gameMapper) {
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
    events.getGameNameEvent().register(this::setGameName);
    events.getStartNewGameEvent().register(this::create);
    events.getSelectSavedGameEvent().register(this::create);
    events.getSaveGameEvent().register(this::save);
    events.getSelectScenarioEvent().register(this::setScenario);
    events.getSelectSideEvent().register(this::setHumanSide);
  }

  private void setGameName(final GameNameEvent gameEvent) {
    gameName = gameEvent.gameName();
    log.debug("Game Service received gameNameEvent, game set to: '{}'", gameEvent.gameName());
  }

  private void create(final StartNewGameEvent startNewGameEvent) {
    game = new Game(gameName);
    log.debug("Game Service received startNewGameEvent");
  }

  private void create(final SelectSavedGameEvent selectSavedGameEvent) {
    game = selectSavedGameEvent.getGame();
    log.debug("Game Service received selectedSavedGameEvent");
  }

  private void save(final SaveGameEvent gameSaveEvent) {
    game.setId(gameSaveEvent.getId());
    var gameEntity = gameMapper.toEntity(game);

    gameRepository.save(gameEntity);
    log.debug("Game Service received gameSaveEvent, save: '{}'", game.getId());
  }

  private void setScenario(final SelectScenarioEvent selectScenarioEvent) {
    game.setScenario(selectScenarioEvent.getScenario());
    log.debug("Game Service received scenarioEvent, scenario set to: '{}'", selectScenarioEvent.getScenario().getTitle());
  }

  private void setHumanSide(final SelectSideEvent selectSideEvent) {
    game.setHumanSide(selectSideEvent.getSide());
    log.debug("Game Service received sideEvent, side set to: '{}'", selectSideEvent.getSide());
  }
}
