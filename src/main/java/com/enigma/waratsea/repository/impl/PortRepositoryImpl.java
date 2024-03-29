package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.PortRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PortRepositoryImpl implements PortRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public PortEntity get(final Id portId) {
    var filePath = getFilePath(portId);

    return readPort(filePath);
  }

  @Override
  public void save(final String gameId, final PortEntity port) {
    var portId = port.getId();
    var filePath = getFilePath(portId);

    writePort(gameId, filePath, port);
  }

  private PortEntity readPort(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read port: '{}'", filePath);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to create port: " + filePath);
    }
  }

  private void writePort(final String gameId, final FilePath filePath, final PortEntity port) {
    try (var out = getOutputStream(gameId, filePath);
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save port for game: '{} to path: '{}'", gameId, filePath);
      var json = toJson(port);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save port for game: " + gameId + "to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getInputStream(filePath);
  }

  private OutputStream getOutputStream(final String gameId, final FilePath filePath) throws FileNotFoundException {
    return dataProvider.getOutputStream(gameId, filePath);
  }

  private PortEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();
    var port = gson.fromJson(bufferedReader, PortEntity.class);

    log.debug("load port: '{}'", port.getId());

    return port;
  }

  private String toJson(final PortEntity port) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(port);
  }

  private FilePath getFilePath(final Id portId) {
    var portDirectory = gamePaths.getPortDirectory();

    return FilePath.builder()
        .baseDirectory(portDirectory)
        .side(portId.getSide())
        .fileName(portId.getName())
        .build();
  }
}
