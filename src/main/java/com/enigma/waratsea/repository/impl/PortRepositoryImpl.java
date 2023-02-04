package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.PortRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
public class PortRepositoryImpl implements PortRepository {
  private final DataProvider dataProvider;
  private final String portDirectory;

  @Inject
  public PortRepositoryImpl(final GamePaths gamePaths,
                            final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.portDirectory = gamePaths.getPortDirectory();
  }


  @Override
  public PortEntity get(Id portId) {
    var filePath = getFilePath(portId);

    return readPort(filePath);
  }

  @Override
  public void save(String gameId, PortEntity port) {
    var filePath = getFilePath(port);

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
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save port to path: '{}'", path);
      var json = toJson(port);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save port to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
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
    return FilePath.builder()
        .baseDirectory(portDirectory)
        .side(portId.getSide())
        .fileName(portId.getName())
        .build();
  }

  private FilePath getFilePath(final PortEntity port) {
    return FilePath.builder()
        .baseDirectory(portDirectory)
        .side(port.getId().getSide())
        .fileName(port.getId().getName())
        .build();
  }
}
