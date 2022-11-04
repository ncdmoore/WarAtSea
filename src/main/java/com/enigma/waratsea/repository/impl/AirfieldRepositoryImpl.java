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
import java.nio.file.Paths;
import java.util.Locale;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Slf4j
@Singleton
public class AirfieldRepositoryImpl implements AirfieldRepository {
  private final DataNames dataNames;
  private final DataProvider dataProvider;

  @Inject
  public AirfieldRepositoryImpl(final DataNames dataNames,
                                final DataProvider dataProvider) {
    this.dataNames = dataNames;
    this.dataProvider = dataProvider;
  }

  @Override
  public AirfieldEntity get(final Id airfieldId) {
    return createAirfield(airfieldId);
  }

  @Override
  public void save(final String gameId, final AirfieldEntity airfield) {
    var directory = determineDirectory(gameId, airfield);
    dataProvider.createDirectoryIfNeeded(directory);
    saveAirfield(directory, airfield);
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

  private void saveAirfield(final Path directory, final AirfieldEntity airfield) {
    var airfieldName = airfield.getId().getName();
    var filePath = Paths.get(directory.toString(), airfieldName + JSON_EXTENSION);

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save airfield: '{} to path: '{}'", airfield.getId(), directory);
      writeAirfield(writer, airfield);
    } catch (IOException e) {
      throw new GameException("Unable to save " + airfield.getId() + " to path: " + filePath, e);
    }
  }

  private InputStream getAirfieldInputStream(final Id airfieldId) {
    var sidePath = airfieldId.getSide().toLower();
    var fileName = airfieldId.getName() + JSON_EXTENSION;
    var airfieldBasePath = dataNames.getAirfieldDirectory();
    var airfieldPath = Paths.get(airfieldBasePath, sidePath, fileName).toString();

    return dataProvider.getDataInputStream(airfieldPath);
  }

  private AirfieldEntity readAirfield(final BufferedReader bufferedReader) {
    var gson = new Gson();
    var airfield = gson.fromJson(bufferedReader, AirfieldEntity.class);

    log.debug("load airfield: '{}'", airfield.getId());

    return airfield;
  }

  private void writeAirfield(final OutputStreamWriter writer, final AirfieldEntity airfield) throws IOException {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    String json = gson.toJson(airfield);
    writer.write(json);
  }

  private Path determineDirectory(final String gameId, final AirfieldEntity airfield) {
    var savedGameDirectory = dataNames.getSavedGameDirectory();
    var airfieldDirectoryName = dataNames.getAirfieldDirectory();
    var side = airfield.getId().getSide().toString().toLowerCase(Locale.ROOT);
    return Paths.get(savedGameDirectory, gameId, airfieldDirectoryName, side);
  }
}
