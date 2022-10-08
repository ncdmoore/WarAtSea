package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadPlayerEvent;
import com.enigma.waratsea.model.player.ComputerPlayer;
import com.enigma.waratsea.model.player.HumanPlayer;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.PlayerService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.model.Side.NEUTRAL;

@Slf4j
@Singleton
public class PlayerServiceImpl implements PlayerService {
  private final GameService gameService;

  @Inject
  public PlayerServiceImpl(final Events events,
                           final GameService gameService) {
    this.gameService = gameService;

    events.getLoadPlayerEvent().register(this::handleLoadPlayerEvent);
  }

  private void handleLoadPlayerEvent(final LoadPlayerEvent event) {
    log.info("Player service received load player event");

    createPlayers();
  }

  private void createPlayers() {
    var game = gameService.getGame();
    var side = game.getHumanSide();

    var human = new HumanPlayer(side);
    var opponent = new ComputerPlayer(side.oppositeSide());
    var neutral = new ComputerPlayer(NEUTRAL);

    game.addPlayer(human);
    game.addPlayer(opponent);
    game.addPlayer(neutral);
  }
}
