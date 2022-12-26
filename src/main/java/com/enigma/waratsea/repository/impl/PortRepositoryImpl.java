package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.PortRepository;
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
public class PortRepositoryImpl implements PortRepository {
  private final DataProvider dataProvider;
  private final String portDirectory;

  @Inject
  public PortRepositoryImpl(final GamePaths dataGamePaths,
                            final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.portDirectory = dataGamePaths.getPortDirectory();
  }


  @Override
  public PortEntity get(Id portId) {
    return readPort(portId);
  }

  @Override
  public void save(String gameId, PortEntity port) {
    var id = port.getId();
    var directory = dataProvider.getSaveDirectory(gameId, id, portDirectory);
    writePort(directory, port);
  }

  private PortEntity readPort(final Id portId) {
    try (var in = getInputStream(portId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read port: '{}'", portId);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to create port: " + portId);
    }
  }

  private void writePort(final Path directory, final PortEntity port) {
    var id = port.getId();
    var filePath = dataProvider.getSaveFile(directory, id);

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.info("Save port: '{}' to path: '{}'", id, directory);
      var json = toJson(port);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save " + id + " to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final Id portId) {
    return dataProvider.getDataInputStream(portId, portDirectory);
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
}
