package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.repository.ShipRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ShipRepositoryImpl implements ShipRepository {
  private final ResourceProvider resourceProvider;
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public ShipEntity get(final Id shipId, final ShipType shipType) {
    var filePath = getFilePath(shipId);

    return readShip(filePath, shipType);
  }

  @Override
  public List<ShipRegistryEntity> getRegistry(final Side side, final ShipType shipType) {
    var filePath = getFilePath(side, shipType);

    return readRegistry(filePath);
  }

  @Override
  public void save(final String gameId, final ShipEntity ship) {
    var shipId = ship.getId();
    var filePath = getFilePath(shipId);

    writeShip(gameId, filePath, ship);
  }

  private ShipEntity readShip(final FilePath filePath, final ShipType shipType) {
    try (var in = getShipInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read ship: '{}'", filePath);
      return toEntity(br, shipType);
    } catch (IOException e) {
     throw new GameException("Unable to load ship: " + filePath, e);
    }
  }

  private void writeShip(final String gameId, final FilePath filePath, final ShipEntity ship) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save ship to path: '{}'", path);
      var json = toJson(ship);
      writer.write(json);

    } catch (IOException e) {
      throw new GameException("Unable to save ship to path: " + path, e);
    }
  }

  private List<ShipRegistryEntity> readRegistry(final FilePath filePath) {
    try (var in = getRegistryInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read ship registry: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read ship registry: '{}'", filePath);
      return Collections.emptyList();
    }
  }

  private InputStream getRegistryInputStream(final FilePath filePath) {
    return resourceProvider.getDefaultResourceInputStream(filePath);
  }

  private InputStream getShipInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private ShipEntity toEntity(final BufferedReader bufferedReader, final ShipType shipType) {
    var gson = new Gson();
    return gson.fromJson(bufferedReader, shipType.getClazz());
  }

  private List<ShipRegistryEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<ShipRegistryEntity>>() {
    }.getType();

    var gson = new Gson();
    return gson.fromJson(bufferedReader, collectionType);
  }

  private String toJson(final ShipEntity ship) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(ship);
  }

  private FilePath getFilePath(final Id shipId) {
    var shipDirectory = gamePaths.getShipDirectory();

    return FilePath.builder()
        .baseDirectory(shipDirectory)
        .side(shipId.getSide())
        .fileName(shipId.getName())
        .build();
  }

  private FilePath getFilePath(final Side side, final ShipType shipType) {
    var shipRegistryDirectory = gamePaths.getShipRegistryDirectory();

    return FilePath.builder()
        .baseDirectory(shipRegistryDirectory)
        .side(side)
        .fileName(shipType.toLower())
        .build();
  }
}
