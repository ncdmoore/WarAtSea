package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.event.CreatePlayerEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.player.ComputerPlayer;
import com.enigma.waratsea.model.player.HumanPlayer;
import com.enigma.waratsea.model.player.Player;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.MtbFlotillaService;
import com.enigma.waratsea.service.PlayerService;
import com.enigma.waratsea.service.PortService;
import com.enigma.waratsea.service.RegionService;
import com.enigma.waratsea.service.SquadronService;
import com.enigma.waratsea.service.SubmarineFlotillaService;
import com.enigma.waratsea.service.TaskForceService;
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
  private final SubmarineFlotillaService submarineFlotillaService;
  private final MtbFlotillaService mtbFlotillaService;
  private final SquadronService squadronService;

  //CHECKSTYLE:OFF
  @Inject
  public PlayerServiceImpl(final Events events,
                           final GameService gameService,
                           final RegionService regionService,
                           final AirfieldService airfieldService,
                           final PortService portService,
                           final TaskForceService taskForceService,
                           final SubmarineFlotillaService submarineFlotillaService,
                           final MtbFlotillaService mtbFlotillaService,
                           final SquadronService squadronService) {
    this.gameService = gameService;
    this.regionService = regionService;
    this.airfieldService = airfieldService;
    this.portService = portService;
    this.taskForceService = taskForceService;
    this.submarineFlotillaService = submarineFlotillaService;
    this.mtbFlotillaService = mtbFlotillaService;
    this.squadronService = squadronService;

    events.getCreatePlayerEvent().register(this::handleCreatePlayerEvent);
  }
  //CHECKSTYLE:ON

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

  private void configurePlayer(final Player player) {
    addNations(player);
    addAirfields(player);
    addPorts(player);
    addTaskForces(player);
    addSubmarineFlotillas(player);
    addMtbFlotillas(player);
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

  private void addSubmarineFlotillas(final Player player) {
    var side = player.getSide();
    var submarineFlotillas = submarineFlotillaService.get(side);
    player.setSubmarineFlotillas(submarineFlotillas);
  }

  private void addMtbFlotillas(final Player player) {
    var side = player.getSide();
    var mtbFlotillas = mtbFlotillaService.get(side);
    player.setMtbFlotillas(mtbFlotillas);
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
