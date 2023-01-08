package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.CreatePlayerEvent;
import com.enigma.waratsea.model.player.ComputerPlayer;
import com.enigma.waratsea.model.player.HumanPlayer;
import com.enigma.waratsea.model.player.Player;
import com.enigma.waratsea.service.*;
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
  private final PortService portService;
  private final TaskForceService taskForceService;

  @Inject
  public PlayerServiceImpl(final Events events,
                           final GameService gameService,
                           final AirfieldService airfieldService,
                           final PortService portService,
                           final TaskForceService taskForceService) {
    this.gameService = gameService;
    this.airfieldService = airfieldService;
    this.portService = portService;
    this.taskForceService = taskForceService;

    events.getCreatePlayerEvent().register(this::handleCreatePlayerEvent);
  }

  private void handleCreatePlayerEvent(final CreatePlayerEvent event) {
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
    addPorts(player);
    addTaskForces(player);
    addToGame(player);
  }

  private void addAirfields(final Player player) {
    var side = player.getSide();
    var airfields = airfieldService.get(side);
    player.setAirfields(airfields);
  }

  private void addPorts(final Player player) {
    var side = player.getSide();
    var ports = portService.get(side);
    player.setPorts(ports);
  }

  private void addTaskForces(final Player player) {
    var side = player.getSide();
    var taskForces = taskForceService.get(side);
    player.setTaskForces(taskForces);
  }

  private void addToGame(final Player player) {
    var game = gameService.getGame();
    game.addPlayer(player);
  }
}
