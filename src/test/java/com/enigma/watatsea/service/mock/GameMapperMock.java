package com.enigma.watatsea.service.mock;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.model.Game;

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;

public class GameMapperMock extends GameMapper {
  @Override
  public GameEntity toEntity(Game game) {
    return new GameEntity(
        game.getGameName(),
        game.getId(),
        game.getScenario().getId(),
        game.getHumanSide(),
        game.getTurn(),
        game.getWeather());
  }

  @Override
  public Game toModel(GameEntity gameEntity) {
    var game = new Game(BOMB_ALLEY);
    game.setId(gameEntity.getId());
    return game;
  }
}
