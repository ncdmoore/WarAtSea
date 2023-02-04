package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.aircraft.AircraftEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AircraftRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
public class AircraftRepositoryImpl implements AircraftRepository {
  private final ResourceProvider resourceProvider;
  private final String aircraftDirectory;

  @Inject
  public AircraftRepositoryImpl(final GamePaths gamePaths,
                                final ResourceProvider resourceProvider) {
    this.resourceProvider = resourceProvider;
    this.aircraftDirectory = gamePaths.getAircraftDirectory();
  }

  @Override
  public AircraftEntity get(Id aircraftId) {
    var filePath = getFilePath(aircraftId);

    return readAircraft(filePath);
  }

  private AircraftEntity readAircraft(final FilePath filePath) {
    log.info("Read aircraft: '{}'", filePath);

    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to create aircraft: " + filePath, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return resourceProvider.getResourceInputStream(filePath);
  }

  private AircraftEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();

    var aircraft = gson.fromJson(bufferedReader, AircraftEntity.class);

    log.debug("load airfield: '{}'", aircraft.getId());

    return aircraft;
  }

  private FilePath getFilePath(final Id airfieldId) {
    return FilePath.builder()
        .baseDirectory(aircraftDirectory)
        .side(airfieldId.getSide())
        .fileName(airfieldId.getName())
        .build();
  }
}
