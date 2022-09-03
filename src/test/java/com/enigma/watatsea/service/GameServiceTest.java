package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.GameServiceImpl;
import com.enigma.watatsea.repository.mock.GameRepositoryMock;
import com.enigma.watatsea.service.mock.WeatherServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
  private GameService gameService;

  private GameRepositoryMock gameRepository;

  @BeforeEach
  void setUp() {
    var events = new Events();
    var weatherService = new WeatherServiceMock();
    gameRepository = new GameRepositoryMock();

    gameService = new GameServiceImpl(events, weatherService, gameRepository);
  }

  @Test
  void testGet() {
    var games = List.of(buildGameEntity());

    gameRepository.setGames(games);

    var result = gameService.get();

    assertEquals(1, result.size());
  }

  private GameEntity buildGameEntity() {
    var turn = Turn.builder().build();
    var weather = Weather.builder().build();
    return new GameEntity(BOMB_ALLEY, "id", 1, AXIS, turn, weather);
  }

}
