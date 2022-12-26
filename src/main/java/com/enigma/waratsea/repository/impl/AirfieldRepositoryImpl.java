package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Slf4j
@Singleton
public class AirfieldRepositoryImpl implements AirfieldRepository {
  private final DataProvider dataProvider;
  private final String airfieldDirectory;

  @Inject
  public AirfieldRepositoryImpl(final GamePaths gamePaths,
                                final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.airfieldDirectory = gamePaths.getAirfieldDirectory();
  }

  @Override
  public AirfieldEntity get(final Id airfieldId) {
    return readAirfield(airfieldId);
  }

  @Override
  public void save(final String gameId, final AirfieldEntity airfield) {
    var id = airfield.getId();
    var directory = dataProvider.getSavedEntityDirectory(gameId, id, airfieldDirectory);
    writeAirfield(directory, airfield);
  }

  private AirfieldEntity readAirfield(final Id airfieldId) {
    try (var in = getInputStream(airfieldId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read airfield: '{}'", airfieldId);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to create airfield: " + airfieldId);
    }
  }

  private void writeAirfield(final Path directory, final AirfieldEntity airfield) {
    var id = airfield.getId();
    var filePath = dataProvider.getSaveFile(directory, id);

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save airfield: '{}' to path: '{}'", id, directory);
      var json = toJson(airfield);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save " + id + " to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final Id airfieldId) {
    return dataProvider.getDataInputStream(airfieldId, airfieldDirectory);
  }

  private AirfieldEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();
    var airfield = gson.fromJson(bufferedReader, AirfieldEntity.class);

    log.debug("load airfield: '{}'", airfield.getId());

    return airfield;
  }

  private String toJson(final AirfieldEntity airfield) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(airfield);
  }
}
