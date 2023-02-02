package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.CreatePlayerEvent;
import com.enigma.waratsea.event.Events;
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
  private final RegionService regionService;
  private final AirfieldService airfieldService;
  private final PortService portService;
  private final TaskForceService taskForceService;
  private final SquadronService squadronService;

  @Inject
  public PlayerServiceImpl(final Events events,
                           final GameService gameService,
                           final RegionService regionService,
                           final AirfieldService airfieldService,
                           final PortService portService,
                           final TaskForceService taskForceService,
                           final SquadronService squadronService) {
    this.gameService = gameService;
    this.regionService = regionService;
    this.airfieldService = airfieldService;
    this.portService = portService;
    this.taskForceService = taskForceService;
    this.squadronService = squadronService;

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
    addNations(player);
    addAirfields(player);
    addPorts(player);
    addTaskForces(player);
    addSquadrons(player);
    addToGame(player);
  }

  private void addNations(final Player player) {
    var side = player.getSide();
    var nations = regionService.getNations(side);
    player.setNations(nations);
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

  private void addSquadrons(final Player player) {
    var side = player.getSide();
    var squadrons = squadronService.get(side);
    player.setSquadrons(squadrons);
  }

  private void addToGame(final Player player) {
    var game = gameService.getGame();
    game.addPlayer(player);
  }
}
