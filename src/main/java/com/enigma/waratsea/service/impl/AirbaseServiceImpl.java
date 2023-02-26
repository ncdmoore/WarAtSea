package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.service.AirbaseService;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.TaskForceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Singleton
public class AirbaseServiceImpl implements AirbaseService {
  private final AirfieldService airfieldService;
  private final TaskForceService taskForceService;

  @Inject
  public AirbaseServiceImpl(final AirfieldService airfieldService,
                            final TaskForceService taskForceService) {
    this.airfieldService = airfieldService;
    this.taskForceService = taskForceService;
  }

  @Override
  public Set<Nation> getNations(final Side side) {
    return get().values()
        .stream()
        .filter(airbase -> matchSide(airbase, side))
        .flatMap(airbase -> airbase.getNations().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public Map<Id, Airbase> get() {
    return Side.stream()
        .flatMap(this::getAirbasesForSide)
        .collect(Collectors.toMap(Airbase::getId, airbase -> airbase));
  }

  private Stream<Airbase> getAirbasesForSide(final Side side) {
    var airbases = airfieldService.get(side)
        .stream()
        .map(airfield -> (Airbase) airfield)
        .collect(Collectors.toSet());

    var shipsThatAreAirbases = taskForceService.get(side)
        .stream()
        .flatMap(taskForce -> taskForce.getAirbases().stream())
        .collect(Collectors.toSet());

    airbases.addAll(shipsThatAreAirbases);

    return airbases.stream();
  }

  private boolean matchSide(final Airbase airbase, final Side side) {
    return airbase.getId().getSide() == side;
  }
}
