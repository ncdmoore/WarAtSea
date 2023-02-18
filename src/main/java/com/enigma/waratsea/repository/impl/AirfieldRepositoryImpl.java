package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
public class AirfieldRepositoryImpl implements AirfieldRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Inject
  public AirfieldRepositoryImpl(final GamePaths gamePaths,
                                final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.gamePaths = gamePaths;
  }

  @Override
  public AirfieldEntity get(final Id airfieldId) {
    var filePath = getFilePath(airfieldId);

    return readAirfield(filePath);
  }

  @Override
  public void save(final String gameId, final AirfieldEntity airfield) {
    var filePath = getFilePath(airfield);

    writeAirfield(gameId, filePath, airfield);
  }

  private AirfieldEntity readAirfield(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read airfield: '{}'", filePath);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to create airfield: " + filePath, e);
    }
  }

  private void writeAirfield(final String gameId, final FilePath filePath, final AirfieldEntity airfield) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save airfield  path: '{}'", path);
      var json = toJson(airfield);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save airfield to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
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

  private FilePath getFilePath(final Id airfieldId) {
    var airfieldDirectory = gamePaths.getAirfieldDirectory();

    return FilePath.builder()
        .baseDirectory(airfieldDirectory)
        .side(airfieldId.getSide())
        .fileName(airfieldId.getName())
        .build();
  }

  private FilePath getFilePath(final AirfieldEntity airfield) {
    var airfieldDirectory = gamePaths.getAirfieldDirectory();

    return FilePath.builder()
        .baseDirectory(airfieldDirectory)
        .side(airfield.getId().getSide())
        .fileName(airfield.getId().getName())
        .build();
  }
}
