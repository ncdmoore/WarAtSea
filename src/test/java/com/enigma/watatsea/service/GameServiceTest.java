package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.repository.GameRepository;
import com.enigma.waratsea.service.impl.GameServiceImpl;
import com.enigma.waratsea.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
  @InjectMocks
  private GameServiceImpl gameService;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  @SuppressWarnings("unused")
  @Mock
  private WeatherService weatherService;

  @Mock
  private GameRepository gameRepository;

  @Mock
  private GameMapper gameMapper;

  @Test
  void testGet() {
    var id = "id";
    var gameEntity = buildGameEntity(id);
    var gameModel = toModel(gameEntity);
    var games = List.of(gameEntity);

    given(gameRepository.get()).willReturn(games);
    given(gameMapper.toModel(gameEntity)).willReturn(gameModel);

    var result = gameService.get();

    assertEquals(1, result.size());
    assertEquals(id, result.get(0).getId());
  }

  private GameEntity buildGameEntity(final String id) {
    var turn = Turn.builder().build();
    var weather = Weather.builder().build();
    return new GameEntity(BOMB_ALLEY, id, 1, AXIS, turn, weather);
  }

  private Game toModel(GameEntity gameEntity) {
    var game = new Game(BOMB_ALLEY);
    game.setId(gameEntity.getId());
    return game;
  }
}
