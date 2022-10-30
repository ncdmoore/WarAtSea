package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadPlayerEvent;
import com.enigma.waratsea.model.player.ComputerPlayer;
import com.enigma.waratsea.model.player.HumanPlayer;
import com.enigma.waratsea.model.player.Player;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.PlayerService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.enigma.waratsea.model.Side.NEUTRAL;

@Slf4j
@Singleton
public class PlayerServiceImpl implements PlayerService {
  private final GameService gameService;
  private final AirfieldService airfieldService;

  @Inject
  public PlayerServiceImpl(final Events events,
                           final GameService gameService,
                           final AirfieldService airfieldService) {
    this.gameService = gameService;
    this.airfieldService = airfieldService;

    events.getLoadPlayerEvent().register(this::handleLoadPlayerEvent);
  }

  private void handleLoadPlayerEvent(final LoadPlayerEvent event) {
    log.info("Player service received load player event");

    createPlayers();
  }

  private void createPlayers() {
    var game = gameService.getGame();
    var humanSide = game.getHumanSide();

    var human = new HumanPlayer(humanSide);
    var opponent = new ComputerPlayer(humanSide.oppositeSide());
    var neutral = new ComputerPlayer(NEUTRAL);

    var players = List.of(human, opponent, neutral);

    players.forEach(this::configurePlayer);
  }

  private void configurePlayer(Player player) {
    addAirfields(player);
    addToGame(player);
  }

  private void addAirfields(final Player player) {
    var side = player.getSide();
    var airfields = airfieldService.get(side);
    player.setAirfields(airfields);
  }

  private void addToGame(final Player player) {
    var game = gameService.getGame();
    game.addPlayer(player);
  }
}
