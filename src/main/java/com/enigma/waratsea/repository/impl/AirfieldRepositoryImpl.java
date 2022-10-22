package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Slf4j
@Singleton
public class AirfieldRepositoryImpl implements AirfieldRepository {
  private final ResourceNames resourceNames;
  private final ResourceProvider resourceProvider;

  @Inject
  public AirfieldRepositoryImpl(final ResourceNames resourceNames,
                                final ResourceProvider resourceProvider) {
    this.resourceNames = resourceNames;
    this.resourceProvider = resourceProvider;
  }

  @Override
  public List<AirfieldEntity> get(final List<Id> airfieldIds) {
    return airfieldIds
        .stream()
        .map(this::createAirfield)
        .toList();
  }

  @Override
  public AirfieldEntity get(final Id airfieldId) {
    return createAirfield(airfieldId);
  }

  private AirfieldEntity createAirfield(final Id airfieldId) {
    try (var in = getAirfieldInputStream(airfieldId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read airfield for airfield: '{}'", airfieldId);
      return readAirfield(br);
    } catch (IOException e) {
      throw new GameException("Unable to create airfield: " + airfieldId);
    }
  }

  private InputStream getAirfieldInputStream(final Id airfieldId) {
    var sidePath = airfieldId.getSide().toLower();
    var fileName = airfieldId.getName() + JSON_EXTENSION;
    var airfieldBasePath = resourceNames.getAirfieldDirectory();
    var airfieldPath = Paths.get(airfieldBasePath, sidePath, fileName).toString();

    return resourceProvider.getResourceInputStream(airfieldPath);
  }

  private AirfieldEntity readAirfield(final BufferedReader bufferedReader) {
    Gson gson = new Gson();
    var airfield = gson.fromJson(bufferedReader, AirfieldEntity.class);

    log.debug("load airfield: '{}'", airfield.getId());

    return airfield;
  }
}
