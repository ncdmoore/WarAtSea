package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.repository.AircraftRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class AircraftRepositoryImpl implements AircraftRepository {

  @Inject
  public AircraftRepositoryImpl(final ResourceNames resourceNames,
                                final ResourceProvider resourceProvider) {

  }

  @Override
  public Aircraft get(Id aircraftId) {
    return null;
  }
}
