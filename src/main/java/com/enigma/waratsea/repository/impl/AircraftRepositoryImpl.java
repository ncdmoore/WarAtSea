package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.aircraft.AircraftEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AircraftRepository;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AircraftRepositoryImpl implements AircraftRepository {
  private final GamePaths gamePaths;
  private final ResourceProvider resourceProvider;

  @Override
  public AircraftEntity get(final Id aircraftId) {
    var filePath = getFilePath(aircraftId);

    return readAircraft(filePath);
  }

  private AircraftEntity readAircraft(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read aircraft: '{}'", filePath);
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
    var aircraftDirectory = gamePaths.getAircraftDirectory();

    return FilePath.builder()
        .baseDirectory(aircraftDirectory)
        .side(airfieldId.getSide())
        .fileName(airfieldId.getName())
        .build();
  }
}
